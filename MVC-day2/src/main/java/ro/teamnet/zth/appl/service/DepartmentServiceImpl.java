package ro.teamnet.zth.appl.service;

import ro.teamnet.zth.appl.dao.DepartmentDao;
import ro.teamnet.zth.appl.dao.EmployeeDao;
import ro.teamnet.zth.appl.domain.Department;

import java.util.List;

/**
 * Created by Robert.Dumitrescu on 7/24/2017.
 */
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentDao departmentDao = new DepartmentDao();


    public List<Department> findAll() {
        return departmentDao.findAllDepartments();
    }

    public Department findOne(Long departmentId) {
        return departmentDao.findDepartmentById(departmentId);
    }
}