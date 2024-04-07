package Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Contact.Contact;

@Dao
public interface ContactDao {
    @Insert
    public long addContact(Contact contact);
    @Update
    public void updateContact(Contact contact);
    @Delete
    public void deleteContact(Contact contact);

    @Query("select * from contacts")
    public List<Contact> getAll();

    @Query("select * from contacts where contact_id ==:contact_Id")
    public Contact getContact(long contact_Id);
}
