package space.mephi.database

import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import space.mephi.Config
import space.mephi.entities.MongoDocument
import org.litote.kmongo.*
import kotlin.reflect.KProperty1

object Database {

    private val client = KMongo.createClient(Config.MONGO_URL)
    val database: MongoDatabase = client.getDatabase(Config.DATABASE_NAME)

    // get all elements of DocumentType
    inline fun <reified DocumentType : MongoDocument> getCol(): MongoCollection<DocumentType> {
        return database.getCollection<DocumentType>(DocumentType::class.simpleName!!.lowercase())
    }

    // save document in db
    inline fun <reified DocumentType : MongoDocument> save(document: DocumentType): DocumentType {
        return document.apply { getCol<DocumentType>().insertOne(document) }
    }

    // find document by id
    inline fun <reified DocumentType : MongoDocument> findById(id: String): DocumentType? {
        return getCol<DocumentType>().findOneById(id)
    }

    // find document by one of the properties
    inline fun <reified DocumentType : MongoDocument, FieldType> findOne(
        field: KProperty1<DocumentType, FieldType>,
        value: FieldType
    ): DocumentType? {
        return getCol<DocumentType>().findOne(field eq value)
    }

    // find document by one of the properties
    inline fun <reified DocumentType : MongoDocument, FieldType> findAll(
        field: KProperty1<DocumentType, FieldType>,
        value: FieldType
    ): FindIterable<DocumentType> {
        return getCol<DocumentType>().find(field eq value)
    }
}