interface Entity {
    fun method()
}

class ConcreteEntity1: Entity {
    override fun method() {
        // Logic of ConcreteEntity1
    }
}

class ConcreteEntityN: Entity {
    override fun method() {
        // Logic of ConcreteEntityN
    }
}

class EntityFactory {
    companion object {
        fun createEntity(type: String): Entity {
            return when(type) {
                "concreteEntity1" -> ConcreteEntity1()
                "concreteEntityN" -> ConcreteEntityN()
                else -> throw IllegalArgumentException("Unknown type")
            }
        }
    }
}

fun main(){
    val entityType = "concreteEntity1"
    val entity = EntityFactory.createEntity(entityType)
    // Here entity has type ConcreteEntity1
    entity.method()
    // Here a method of ConcreteEntity1 has been called
}

// Test 1
// @Test
fun createConcreteEntityTest() {
    val entity1 = EntityFactory.createEntity("concreteEntity1")
    assert(entity1 is ConcreteEntity1)
    val entity2 = EntityFactory.createEntity("concreteEntityN")
    assert(entity2 is ConcreteEntityN)
}


// Test 2
// @Test(expected = IllegalArgumentException::class)
fun createUnknownEntityTest() {
    EntityFactory.createEntity("unknownEntity")
}
