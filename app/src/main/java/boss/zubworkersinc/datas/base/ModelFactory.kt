package boss.zubworkersinc.datas.base

interface ModelFactory{
    fun getModelInitializer():ModelInitializer
    fun getModelLoader():ModelLoader
}