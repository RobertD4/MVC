package ro.teamnet.zth.appl.service;

import ro.teamnet.zth.appl.dao.EmployeeDao;
import ro.teamnet.zth.appl.dao.LocationDao;
import ro.teamnet.zth.appl.domain.Location;

import java.util.List;

/**
 * Created by Robert.Dumitrescu on 7/24/2017.
 */
public class LocationServiceImpl implements LocationService {

    private final LocationDao locationDao = new LocationDao();

    public List<Location> findAll() {
        return locationDao.getAllLocations();
    }

    public Location findOne(Long locationId) {
        return locationDao.getLocationById(locationId);
    }
}
