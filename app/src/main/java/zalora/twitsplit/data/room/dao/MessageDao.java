package zalora.twitsplit.data.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import zalora.twitsplit.data.room.entity.MessageModel;

@Dao
public interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMessage(MessageModel... messageModels);

    @Query("SELECT * FROM message_model ORDER BY _group_key ASC, _message ASC")
    Flowable<List<MessageModel>> getAllMessage();

    @Query("SELECT * FROM message_model ORDER BY _created DESC LIMIT 1")
    Single<MessageModel> getLastMessage();
}
