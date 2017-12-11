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

import de.greenrobot.event.EventBus;
import hu.bme.wlassits.budget.model.Globals;
import hu.bme.wlassits.budget.model.Income;
import hu.bme.wlassits.budget.model.Outlay;
import hu.bme.wlassits.budget.model.dbmodels.DbEntity;
import hu.bme.wlassits.budget.model.event.DatabaseChangedEvent;

public class DbHelper {
    private static final String TAG = "DbHelper";

    public static DatabaseReference database = FirebaseDatabase.getInstance().getReference();


    public static void initDb() {

        database.child("dbEntities").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                if (dataSnapshot != null && dataSnapshot.child("type").getValue() != null) {
                    if (Globals.income_types.toString().contains(dataSnapshot.child("type").getValue().toString())) {
                        Income i = getIncomeFromSnapshot(dataSnapshot);
                        Globals.incomes.add(i);

                    } else if (Globals.outlay_types.toString().contains(dataSnapshot.child("type").getValue().toString())) {
                        Outlay o = getOutlayFromSnapshot(dataSnapshot);
                        Globals.outlays.add(o);
                    }
                }
                EventBus.getDefault().post(new DatabaseChangedEvent());
            }

            //Megváltozott egy elem, ezért ezt módosítani kell a listában is
            //megkeressük, hogy melyik volt az indexe (az id-ja uyge unique)
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                if (dataSnapshot != null && dataSnapshot.child("type").getValue() != null) {
                    if (Globals.income_types.toString().contains(dataSnapshot.child("type").getValue().toString())) {
                        Income i = getIncomeFromSnapshot(dataSnapshot);
                       Globals.incomes.set(findItemInList(false, i),i);

                    } else if (Globals.outlay_types.toString().contains(dataSnapshot.child("type").getValue().toString())) {
                        Outlay o = getOutlayFromSnapshot(dataSnapshot);
                        Globals.outlays.set(findItemInList(true, o),o);
                    }
                }
                EventBus.getDefault().post(new DatabaseChangedEvent());
            }


            //Töröltek egy elemet, ezért törölnünk kell a Global listából is.
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.child("type").getValue() != null) {
                    if (Globals.income_types.toString().contains(dataSnapshot.child("type").getValue().toString())) {
                        Income i = getIncomeFromSnapshot(dataSnapshot);
                        Globals.incomes.remove(findItemInList(false, i));

                    } else if (Globals.outlay_types.toString().contains(dataSnapshot.child("type").getValue().toString())) {
                        Outlay o = getOutlayFromSnapshot(dataSnapshot);
                        Globals.outlays.remove(findItemInList(true, o));
                    }
                }
                EventBus.getDefault().post(new DatabaseChangedEvent());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
            }
        });


    }

    public static int findItemInList(boolean isOutlay, Object obj) {
        int pos = 0;
        if (isOutlay) {
            Outlay o = (Outlay) obj;
            for (int i = 0; i < Globals.outlays.size(); i++) {
                if (Globals.outlays.get(i).getId().equals(o.getId()))
                    pos = i;
            }
        } else {
            Income inc = (Income) obj;
            for (int i = 0; i < Globals.outlays.size(); i++) {
                if (Globals.outlays.get(i).getId().equals(inc.getId()))
                    pos = i;
            }
        }
        return pos;
    }

    public static void getDataFromDB() {
        Query query = database.child("dbEntities").orderByChild("fbId").equalTo(Globals.user.getFacebookIdentifier());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Globals.incomes = new ArrayList<>();
                    Globals.outlays = new ArrayList<>();
                    addItems(dataSnapshot);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    private static void addItems(DataSnapshot dataSnapshot) {
        for (DataSnapshot data : dataSnapshot.getChildren()) {
            Log.e("getDataFromDB()", data.toString());

            if (data != null && data.child("type").getValue() != null) {
                if (Globals.income_types.toString().contains(data.child("type").getValue().toString())) {
                    Income i = getIncomeFromSnapshot(data);
                    Globals.incomes.add(i);

                } else if (Globals.outlay_types.toString().contains(data.child("type").getValue().toString())) {
                    Outlay o = getOutlayFromSnapshot(data);
                    Globals.outlays.add(o);
                }
            }
        }
    }

    private static Income getIncomeFromSnapshot(DataSnapshot data) {
        Income i = new Income();

        i.setId(data.child("id").getValue().toString());
        i.setDescription(data.child("description").getValue().toString());
        long time = Long.parseLong(data.child("date").child("time").getValue().toString());
        i.setDate(new Date(time));
        i.setValue(Integer.parseInt(data.child("value").getValue().toString()));
        i.setType(data.child("type").getValue().toString());

        return i;
    }

    private static Outlay getOutlayFromSnapshot(DataSnapshot data) {
        Outlay o = new Outlay();

        o.setId(data.child("id").getValue().toString());
        o.setDescription(data.child("description").getValue().toString());
        long time = Long.parseLong(data.child("date").child("time").getValue().toString());
        o.setDate(new Date(time));
        o.setValue(Integer.parseInt(data.child("value").getValue().toString()));
        o.setType(data.child("type").getValue().toString());

        return o;
    }


    public static void removeItemFromDatabase(Object obj) {
        DbEntity dbEntity = convertToDbEntity(obj);
        database.child("dbEntities").child(dbEntity.getId()).removeValue();
    }

    public static void addItemToDatabase(Object obj) {
        DbEntity dbEntity = convertToDbEntity(obj);
        database.child("dbEntities").child(dbEntity.getId()).setValue(dbEntity);
    }

    public static void updateItemInDatabase(Object obj) {
        DbEntity dbEntity = convertToDbEntity(obj);
        database.child("dbEntities").child(dbEntity.getId()).setValue(dbEntity);
    }

    private static DbEntity convertToDbEntity(Object obj) {
        DbEntity dbEntity = new DbEntity();

        if (obj instanceof Outlay) {
            Outlay o = (Outlay) obj;

            dbEntity = new DbEntity(o.getId(), Globals.user.getFacebookIdentifier(),
                    o.getType(), o.getDescription(), String.valueOf(o.getValue()), o.getDate());

        } else if (obj instanceof Income) {
            Income i = (Income) obj;

            dbEntity = new DbEntity(i.getId(), Globals.user.getFacebookIdentifier(),
                    i.getType(), i.getDescription(), String.valueOf(i.getValue()), i.getDate());
        }
        return dbEntity;
    }

}
