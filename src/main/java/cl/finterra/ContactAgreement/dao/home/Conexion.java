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
    this.mongoClient = MongoClients.create("mongodb://localhost:27017");
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

  }

  public MongoCursor<Document> executePreparedQuery() {

    return this.cursor;
  }

  public int executeUpdate(String query) {

    return 1;
  }

  public void prepareUpdate(String query) {

  }

  public int executePreparedUpdate() {

    return 1;
  }

  public void close() {
    if (this.cursor != null) {
      try {
        this.cursor.close();
      } catch (Exception e) {

      }
      this.cursor = null;
    }

    if (this.mongoClient != null) {
      try {
        this.mongoClient.close();
      } catch (Exception e) {

      }
      this.mongoClient = null;
    }
  }
}
