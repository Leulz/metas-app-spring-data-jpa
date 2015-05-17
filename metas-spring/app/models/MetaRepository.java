package models;

import org.springframework.data.repository.CrudRepository;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Provides CRUD functionality for accessing meta. Spring Data auto-magically takes care of many standard
 * operations here.
 */
@Named
@Singleton
public interface MetaRepository extends CrudRepository<Meta, Long> {
}