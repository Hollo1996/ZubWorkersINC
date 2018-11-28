package boss.zubworkersinc.graphics.bitmap

import boss.zubworkersinc.basics.LifeState
import boss.zubworkersinc.basics.NotifiableThread
import boss.zubworkersinc.basics.Position
import boss.zubworkersinc.graphics.bitmap.little.BLFieldRepresentation
import boss.zubworkersinc.models.ModelContainer

class UpdaterThread() : NotifiableThread("Updater") {

    override fun run() {
        super.run()

        var changed = false
        synchronized(BitLoader.Picture) {
            for (col in 0..BitLoader.Picture.columnSize) {
                for (lin in 0..BitLoader.Picture.lineSize) {
                    BitLoader.Picture[col, lin] = 0
                }
            }
        }

        while (BitLoader.state != LifeState.Created) {
            synchronized(BitLoader.Picture)
            {
                ////println("\n I jast started Updating!")

                for (mapCord in ModelContainer.fieldsMap.keys) {
                    if (ModelContainer.fieldsMap[mapCord]!!.Modified()) {
                        changed = true
                        BitLoader.Picture.drawOn(
                            ((ModelContainer.fieldsMap[mapCord]?.representation) as BLFieldRepresentation).representation,
                            Position(mapCord.column*BitLoader.tileSizeInPixel.column, mapCord.line*BitLoader.tileSizeInPixel.line)
                        )
                    }
                }
            }

            if (changed) {
                BitLoader.surface.painter?.Notify("Update")
                changed = false
            }
            bufferLock.Wait()
        }
    }
}