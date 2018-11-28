package boss.zubworkersinc.models.map

import boss.zubworkersinc.basics.Direction
import boss.zubworkersinc.basics.Position
import boss.zubworkersinc.controls.Liquid
import boss.zubworkersinc.graphics.base.FieldRepresentation
import boss.zubworkersinc.models.ModelContainer
import boss.zubworkersinc.models.features.Feature
import boss.zubworkersinc.models.movables.Box
import boss.zubworkersinc.models.movables.Movable
import boss.zubworkersinc.models.movables.MovableVisitor
import boss.zubworkersinc.models.movables.Worker
import boss.zubworkersinc.upperleayer.Game
import boss.zubworkersinc.upperleayer.GameState

class Field(val position: Position, var representation: FieldRepresentation) : MovableVisitor {
    data class RepresentableData(
        var Self: Field? = null,
        var onThis: Movable? = null,
        var functionality: Feature? = null,
        var IsIsolated: Boolean = false,
        var friction: Double = (Liquid.Honey.friction + Liquid.Oil.friction) / 2
    ) {
        private var mod_data = true
        var modified: Boolean
            get() {
                synchronized(mod_data) { return mod_data; }
            }
            set(value) {
                synchronized(mod_data) { mod_data = value; }
            }

    }

    data class TemporaryData(
        var remainedStrength: Double? = null,
        var enter: Direction? = null
    ) {}

    //Most important data
    var rep: RepresentableData = RepresentableData()
    var temp: TemporaryData = TemporaryData()
    var neighbours = mutableMapOf<Direction, Position>()

    init {
        rep.Self = this
        representation.Data = rep

    }

    fun AddNeighbour(position: Position, direction: Direction, forced: Boolean) {
        if (Game.state != GameState.Inicialization || (!forced && neighbours.containsKey(direction)))
            return

        neighbours[direction] = position

        rep.modified = true
    }

    fun AddFeature(_functionality: Feature) {
        if (rep.functionality == null) {
            rep.functionality = _functionality
            rep.modified = true
        }
    }

    // Adds a Movable object to the field
    fun AddMoveable(moveable: Movable) {
        if (rep.onThis == null) {
            rep.onThis = moveable
            moveable.setField(this)
            if (rep.functionality != null)
                rep.functionality?.Interact?.invoke(moveable)
            rep.modified = true
        }
    }

    operator fun get(d: Direction): Position? = neighbours[d]
    fun IsEmpty(): Boolean = rep.onThis == null
    fun Modified(): Boolean = rep.modified

    override fun visit(b: Box) {
        val neighbour = ModelContainer.fieldsMap[neighbours[temp.enter] as Position]
        if (neighbours.containsKey(temp.enter) && neighbour?.GetIsolated() == false) {
            neighbour.Push(temp.enter as Direction, b, temp.remainedStrength as Double)
        }
    }

    override fun visit(w: Worker) {
        val neighbour = ModelContainer.fieldsMap[neighbours[temp.enter] as Position]
        if (neighbours.containsKey(temp.enter) && !(neighbour?.rep?.IsIsolated ?: true) && neighbour?.IsEmpty() ?: false) {
            neighbour?.Push(temp.enter as Direction, w, temp.remainedStrength as Double)
        }
    }


    fun LeaveRequest(direction: Direction, w: Worker) {
        rep.modified = true
        if (!neighbours.containsKey(direction))
            return
        else {
            val theNeighbour = ModelContainer.fieldsMap[neighbours[direction] as Position]
            if (theNeighbour?.rep?.IsIsolated == false) {
                theNeighbour.Push(direction, w, w.strength)
                if (rep.onThis == null)
                    rep.modified = true
            }
        }
    }

    fun RemoveMoveable() {
        if (rep.onThis != null) {
            rep.onThis = null
            rep.modified = true
        }
    }

    fun RemoveNeighbour(direction: Direction) {
        if (Game.state!=GameState.Inicialization || !neighbours.containsKey(direction))
            return

        neighbours.remove(direction)
        rep.modified = true
    }

    fun GetIsolated(): Boolean {
        return false
    }

    fun DestroyMoveable() {
        if (rep.onThis != null) {
            rep.onThis?.destroy()
        }
    }

    // Isolating the field from its neighbours by changing the isIsolated attribute
    fun Isolate() {
        if (rep.IsIsolated != true) {
            rep.IsIsolated = true
            rep.modified = true
        }
    }

    fun Push(direction: Direction, box: Box, remStrength: Double) {
        if (rep.onThis != null) {
            val onThis = rep.onThis
            if (neighbours.containsKey(direction))
                onThis?.pushed(box, ModelContainer.fieldsMap[neighbours[direction] as Position])
            else
                onThis?.pushed(box, null)
            if ((remStrength - rep.friction) > 0) {
                temp.enter = direction
                temp.remainedStrength = remStrength - rep.friction
                onThis?.accept(this)
            }
        }
        if (rep.onThis == null) {
            rep.onThis = box
            box.setField(this)
            ModelContainer.fieldsMap[neighbours[direction.GetReverse()] as Position]?.RemoveMoveable()
            rep.modified = true

            if (rep.functionality != null) {
                rep.functionality?.Interact?.invoke(box)
            }
        }
    }

    fun Push(direction: Direction, worker: Worker, remStrength: Double) {
        if (rep.onThis != null) {
            rep.onThis?.pushed(worker, null)

            if (rep.onThis != null && ((remStrength - rep.friction) > 0)) {
                temp.enter = direction
                temp.remainedStrength = remStrength - rep.friction
                rep.onThis?.accept(this)
            }
        }

        if (rep.onThis == null) {
            rep.onThis = worker
            worker.setField(this)
            ModelContainer.fieldsMap[neighbours[direction.GetReverse()] as Position]?.RemoveMoveable()
            rep.modified = true

            if (rep.functionality != null) {
                rep.functionality?.Interact?.invoke(worker)
            }
        }
    }


}