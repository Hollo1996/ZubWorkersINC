package boss.zubworkersinc.models

import boss.zubworkersinc.basics.Position
import boss.zubworkersinc.models.features.*
import boss.zubworkersinc.models.map.Field
import boss.zubworkersinc.models.movables.Box
import boss.zubworkersinc.models.movables.Worker

object ModelContainer{
    val fieldsMap = mutableMapOf<Position, Field>()
    val featuresMap = mutableMapOf<Position, Feature>()
    val boxes = mutableListOf<Box>()
    val workers = mutableListOf<Worker>()
}

