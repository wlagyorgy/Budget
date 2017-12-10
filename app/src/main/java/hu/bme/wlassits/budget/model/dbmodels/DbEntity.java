package hu.bme.wlassits.budget.model.dbmodels;

/**
 * Created by Adam Varga on 12/10/2017.
 */


//TODO Gyuri 1. > Olyan adatbázis objektum létrehozása, amely képes tárolni egy-egy entitást (legyen az income vagy outlay)
//TODO Gyuri 1. > Ne felejts el figyelni arra, hogy csak primitív típusokat tudsz tárolni!
//TODO Gyuri 1. > Összesen egy tábla lehet, ezt dokumentációban emeld ki, de oda majd szerintem több táblát írj
public class DbEntity {
    long FB_id;
    String type;
    String description;
    int value;
    long Date;

}
