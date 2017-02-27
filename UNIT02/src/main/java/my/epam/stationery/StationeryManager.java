package my.epam.stationery;

import my.epam.stationery.dao.AbstractDao;
import my.epam.stationery.dao.AssignDao;
import my.epam.stationery.dao.FiledDao;
import my.epam.stationery.entity.HasId;
import my.epam.stationery.model.Assign;
import my.epam.stationery.model.Employee;
import my.epam.stationery.model.Stationery;

import java.util.ArrayList;

public class StationeryManager implements HasId {
    private Long id;
    private int size = 0;
    private AbstractDao<Stationery> stDao;
    private AbstractDao<Employee> emplDao;
    private AssignDao assignDao;

    public StationeryManager() {
    }

    public StationeryManager(AbstractDao<Stationery> stDao, AbstractDao<Employee> emplDao, AssignDao assignDao) {
        this.stDao = stDao;
        this.emplDao = emplDao;
        this.assignDao = assignDao;
    }

    public Stationery[] getForEmployee(Employee employee) {
        if (employee.getId() == null)
            throw new IllegalArgumentException("Employee " + employee.toString() + " not found.");
        else {
            Assign[] assigns = assignDao.getAssignsForEmployeeId(employee.getId());
            Stationery[] result = new Stationery[assigns.length];
            for (int i = 0; i < result.length; i++) {
                result[i] = stDao.getById(assigns[i].getId());
            }
            return result;
        }
    }

    public void addStationery(Stationery stationery, Employee employee) {
        if (employee.getId() == null) throw new IllegalArgumentException("Employee not found.");
        if (stationery.getId() == null) throw new IllegalArgumentException("Stationery not found");

        Long emplId = employee.getId();
        Long stId = stationery.getId();
        Assign exists = assignDao.getAssignByStationeryId(stId);
        if (exists != null) {
            throw new IllegalArgumentException("Stationery with id = " + stId + " already assigned for employee " + exists.getEmployeeId());
        } else {
            assignDao.saveOrUpdate(new Assign(stId, emplId));
        }
    }

    public void addStationery(Stationery[] stationeries, Employee employee) {
        for(Stationery st : stationeries){
            addStationery(st, employee);
        }
    }

    public void removeStationery(Stationery stationery) {
        Assign a = assignDao.getAssignByStationeryId(stationery.getId());
        assignDao.remove(a);
    }

    public void moveStationery(Stationery stationery, Employee newEmployee) {
        removeStationery(stationery);
        addStationery(stationery, newEmployee);
    }

    public Employee getEmployeeOfStationery(Stationery st){
        if(st.getId() == null) throw new IllegalArgumentException("Record not found.");
        Long emplId = assignDao.getAssignByStationeryId(st.getId()).getEmployeeId();
        return emplDao.getById(emplId);
    }

    public AbstractDao<Stationery> getStDao() {
        return stDao;
    }

    public void setStDao(AbstractDao<Stationery> stDao) {
        this.stDao = stDao;
    }

    public AbstractDao<Employee> getEmplDao() {
        return emplDao;
    }

    public void setEmplDao(AbstractDao<Employee> emplDao) {
        this.emplDao = emplDao;
    }

    @Override
    public Long getId() {
        return this.id;
    }

}
