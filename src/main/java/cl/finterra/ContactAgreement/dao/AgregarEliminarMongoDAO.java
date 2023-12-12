package cl.finterra.ContactAgreement.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import cl.finterra.ContactAgreement.entity.AgregarEliminar;
public interface AgregarEliminarMongoDAO extends MongoRepository<AgregarEliminar, String> {
    // Puedes agregar métodos específicos de MongoDB si es necesario
}