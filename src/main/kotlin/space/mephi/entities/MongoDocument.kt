package space.mephi.entities

import org.bson.codecs.pojo.annotations.BsonId

abstract class MongoDocument { // abstract class for all Mongo Entities
    @BsonId
    open var id: String? = null
}