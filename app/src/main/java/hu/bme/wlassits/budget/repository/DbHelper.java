package hu.bme.wlassits.budget.repository;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import hu.bme.wlassits.budget.model.Globals;
import hu.bme.wlassits.budget.model.Outlay;

/**
 * Created by Adam Varga on 12/10/2017.
 */

public class DbHelper {
    private static final String TAG ="DbHelper" ;

    static DatabaseReference reference = FirebaseDatabase.getInstance().getReference();


    public static void initDb(){
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        reference.addChildEventListener(childEventListener);


    }

    public static void getOutlayDataFromDb() {


        Query query = reference.child("dbEntities").orderByChild("fbId").equalTo(Globals.user.getFacebookIdentifier());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Globals.outlays = new ArrayList<>();
                    addOutlayToOutlays(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    private static void addOutlayToOutlays(DataSnapshot dataSnapshot) {
        for (DataSnapshot data : dataSnapshot.getChildren()) {
            Log.e("getOutlayDataFromDb()", data.toString());

            Outlay o = new Outlay();

            o.setId(data.child("id").getValue().toString());
            o.setDescription(data.child("description").getValue().toString());
            long time = Long.parseLong(data.child("date").child("time").getValue().toString());
            o.setDate(new Date(time));
            o.setValue(Integer.parseInt(data.child("value").getValue().toString()));
            o.setType(data.child("type").getValue().toString());
            Globals.outlays.add(o);
        }
    }
}
