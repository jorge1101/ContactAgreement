package cl.finterra.ContactAgreement.dao.home;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

public abstract class Conexion {

  protected MongoClient mongoClient;
  protected MongoDatabase database;
  protected MongoCollection<Document> collection;
  protected MongoCursor<Document> cursor;

  public Conexion() {
    this.mongoClient = MongoClients.create("mongodb://localhost:27017"); // Cambia la URL según tu configuración
  }

  public void connect(String databaseName, String collectionName) {
    this.database = mongoClient.getDatabase(databaseName);
    this.collection = database.getCollection(collectionName);
  }

  public MongoCursor<Document> executeQuery(String query) {
    this.cursor = collection.find(Document.parse(query)).iterator();
    return this.cursor;
  }

  public void prepareQuery(String query) {
    // En MongoDB, la preparación de la consulta no es necesaria
  }

  public MongoCursor<Document> executePreparedQuery() {
    // En MongoDB, la ejecución de la consulta preparada se realiza en el método executeQuery
    return this.cursor;
  }

  public int executeUpdate(String query) {
    // En MongoDB, las operaciones de actualización pueden variar según la implementación específica
    // Por ejemplo, puedes usar updateOne, updateMany, etc.
    // Aquí se muestra un ejemplo básico de actualización:
    // collection.updateOne(Document.parse(query), Document.parse("{ $set: { newField: 'newValue' } }"));
    return 1; // Retorna el número de documentos afectados (puedes ajustarlo según tu lógica)
  }

  public void prepareUpdate(String query) {
    // En MongoDB, la preparación de la actualización no es necesaria
  }

  public int executePreparedUpdate() {
    // En MongoDB, la ejecución de la actualización preparada se realiza en el método executeUpdate
    return 1; // Retorna el número de documentos afectados (puedes ajustarlo según tu lógica)
  }

  public void close() {
    if (this.cursor != null) {
      try {
        this.cursor.close();
      } catch (Exception e) {
        // Manejar la excepción, si es necesario
      }
      this.cursor = null;
    }

    if (this.mongoClient != null) {
      try {
        this.mongoClient.close();
      } catch (Exception e) {
        // Manejar la excepción, si es necesario
      }
      this.mongoClient = null;
    }
  }
}
