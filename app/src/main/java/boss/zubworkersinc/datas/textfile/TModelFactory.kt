package boss.zubworkersinc.datas.textfile

import boss.zubworkersinc.datas.base.ModelFactory
import boss.zubworkersinc.datas.base.ModelInitializer
import boss.zubworkersinc.datas.base.ModelLoader

object TModelFactory :ModelFactory{
    override fun getModelInitializer(): ModelInitializer = TModelInitializer

    override fun getModelLoader(): ModelLoader =TModelLoader
}