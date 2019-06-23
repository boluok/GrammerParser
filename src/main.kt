import java.util.*
import kotlin.collections.HashMap

val nonTerminals = listOf('E','T','V')
val start = "E"
val terminals = listOf(',', '^', '.', '!', '/', '+', '<', '>', '=','-','1','2','3','e','x','I')
val rules: HashMap<String, List<String>> = hashMapOf("E" to listOf(",E", "T"),"T" to listOf("^T", "...E", "!T", "/T", "+T", "V", "VT", "<T", ">T", "=E", "-T"),
    "V" to listOf("1", "2", "3", "e", "I", "x")
    )

fun main(args: Array<String>){

    parser("e^x=1+x^2/3!+...,-I<x<I")
}

fun canParse(sampleString: String):Boolean{
    sampleString.forEach { character ->
        run {
            if(isATerminal(character)){
                return true
            }
        }
    }
    return false
}

fun parser(sampleString:String){

    if(canParse(sampleString)){
        var queue: Queue<Pair<String,MutableList<String>>> = LinkedList()
                    rules.forEach { (rule, productions) ->
                run {
                    if(rule == start){
                        productions.forEach {production ->
                            run {
                                queue.add(Pair(production, mutableListOf(start)))
                            }
                        }
                    }
                }
            }
        while (!queue.isEmpty()){
            val currentnode = queue.remove()
            //node.first = path node.second = value
            if(currentnode.first ==sampleString){
                println()
                print(currentnode.second )
                currentnode.second.forEach { g->
                    run {
                        println(g)
                    }
                }
            }else{
                var terminal = ""
                var terminalCount = 0
                currentnode.first.forEach { character -> run {
                    if(isATerminal(character)){
                            terminalCount++
                            terminal += character
                        }
                    } }
                if((terminalCount == currentnode.first.length) or (currentnode.first.startsWith(sampleString)) or (terminal != "") and !(sampleString.startsWith(terminal)))  {
                        continue
                    }
                var  nonTerm = ""



                nonTerm = getFirstNonTerminal(currentnode.first)



                if(nonTerm != ""){
                        rules.forEach { (rule, productions) ->
                            run {
                                if(rule == nonTerm){
                                    productions.forEach { production -> run {
                                        var cloneNode = mutableListOf<String>()
                                        currentnode.second.forEach { cloneNode.add(it) }
                                        cloneNode.add(currentnode.first)
                                       queue.add(Pair(currentnode.first.replaceFirst(nonTerm,production), cloneNode))
                                    } }
                                }
                            }
                        }
                    }


            }
        }

    }





}



fun isATerminal(character:Char):Boolean {
    var isContainted = false
    terminals.forEach { terminal ->
        run {
            if (terminal == character) {
                isContainted = true
            }
        }
    }
    return isContainted
}

fun getFirstNonTerminal(sampleString: String):String{
    sampleString.forEach { character-> run {
        if(character in nonTerminals){
            return character.toString()
        }
    } }
    return  ""
}
