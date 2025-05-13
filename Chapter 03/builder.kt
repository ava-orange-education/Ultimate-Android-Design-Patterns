class CustomType

class Entity private constructor(
    val property1: CustomType?,
    val propertyN: CustomType?
) {
    class Builder {
        private var property1: CustomType? = null
        private var propertyN: CustomType? = null

        fun setProperty1(property1: CustomType) = apply {
            this.property1 = property1
        }

        fun setPropertyN(propertyN: CustomType) = apply {
            this.propertyN = propertyN
        }

        fun build(): Entity {
            return Entity(property1, propertyN)
        }
    }
}

fun main(){
    val property1 = CustomType()
    val propertyN = CustomType()
    // Here property1 and propertyN of type CustomType can be customized
    val entity = Entity.Builder()
        .setProperty1(property1)
        .setPropertyN(propertyN)
        .build()
    // Here entity has type Entity

    val entity2 = Entity.Builder().build()

    val entity3 = Entity.Builder()
        .setProperty1(property1)
        .build()

    val entity4 = Entity.Builder()
        .setPropertyN(propertyN)
        .build()
}


// Test 1
// @Test
fun testEntityCreation() {
    val property1 = CustomType()
    val propertyN = CustomType()
    val entity = Entity.Builder()
        .setProperty1(property1)
        .setPropertyN(propertyN)
        .build()
    assert(entity.property1 == property1)
    assert(entity.propertyN == propertyN)
}

// Test 2
// @Test
fun testEntityPartialCreation() {
    val property1 = CustomType()
    val entity = Entity.Builder()
        .setProperty1(property1)
        .build()
    assert(entity.property1 == property1)
    assert(entity.propertyN == null)
}
