package com.stackroute.keepnote.dao;

import java.time.LocalDateTime;
import java.util.List;



import com.stackroute.keepnote.model.Note;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;




/*
 * This class is implementing the NoteDAO interface. This class has to be annotated with @Repository
 * annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, thus
 * 				 clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database
 * 					transaction. The database transaction happens inside the scope of a persistence
 * 					context.
 * */

@Repository
@Transactional
public class NoteDAOImpl implements NoteDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.
	 */


	@PersistenceContext
	EntityManager entityManager;


	/*
	 * Save the note in the database(note) table.
	 */

	public boolean saveNote(Note note) {

		if(note!=null) {
			note.setCreatedAt(LocalDateTime.now());
			entityManager.persist(note);
			return true;
		}
		return false;
	}

	/*
	 * Remove the note from the database(note) table.
	 */

	public boolean deleteNote(int noteId) {
		entityManager.remove(entityManager.find(Note.class,noteId));
		return true;
	}

	/*
	 * retrieve all existing notes sorted by created Date in descending
	 * order(showing latest note first)
	 */
	public List<Note> getAllNotes() {
		return entityManager.createQuery("SELECT note FROM Note note").getResultList();

	}

	/*
	 * retrieve specific note from the database(note) table
	 */
	public Note getNoteById(int noteId) {
		return entityManager.find(Note.class,noteId);

	}

	/* Update existing note */

	public boolean UpdateNote(Note note) {
		note.setCreatedAt(LocalDateTime.now());
		entityManager.merge(note);
		return true;

	}

}
