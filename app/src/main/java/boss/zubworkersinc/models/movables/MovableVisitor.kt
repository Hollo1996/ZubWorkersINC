package boss.zubworkersinc.models.movables

interface MovableVisitor {
    fun visit(b: Box)
    fun visit(w: Worker)
}