package boss.zubworkersinc.model

import boss.zubworkersinc.basics.Position
import boss.zubworkersinc.model.features.*
import boss.zubworkersinc.model.map.Field
import boss.zubworkersinc.model.moveables.Box
import boss.zubworkersinc.model.moveables.Worker

object ModelContainer{
    val fieldsMap = mutableMapOf<Position, Field>()
    val featuresMap = mutableMapOf<Position, Feature>()
    val boxes = mutableListOf<Box>()
    val workers = mutableListOf<Worker>()
}

