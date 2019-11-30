package gvfraction

import java.lang.IllegalStateException
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.KClass

object FractionFactory {
    var creator: KClass<FractionOperator>?

    //    var instance: FractionFactory
    init {
        println("Inside FF init")
        // instance = FractionFactory()
        creator = null
    }

    fun registerClass(creator: Any) {
        this.creator = creator as KClass<FractionOperator>
    }

    fun getInstance(): FractionFactory {
        return this
    }

    fun create(vararg values: Any): FractionOperator {
//        if (creator == null)

        try {
            creator?.let {
                if (values.size == 0) {
                    val ctor = it.constructors.find {
                        //println("Param" + it.parameters[0].type.toString())
                        it.parameters.size == 0
                    }
                    ctor?.let {
                        return it.call()
                    }
                } else {
                    var parmType: String
                    if (values[0] is String)
                        parmType = "String"
                    else
                        parmType = "Int"

                    val ctor = it.constructors?.find {
                        //                println("Parm " + it.parameters[0].type)
//                        println("Param" + it.parameters[0].type.toString())
                        it.parameters.size > 0 && it.parameters[0].type.toString().contains(parmType)
                    }
                    ctor?.let { return it.call(values[0]) }
                }
            }
        }
        catch ( e: InvocationTargetException) {
//            println("WHat do we get here " + e)
            throw e.targetException
        }
        throw IllegalStateException("No creator registered")

    }

}