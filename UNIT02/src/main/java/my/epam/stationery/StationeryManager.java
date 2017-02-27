package my.epam.stationery;

import my.epam.stationery.dao.AbstractDao;
import my.epam.stationery.dao.AssignDao;
import my.epam.stationery.dao.FiledDao;
import my.epam.stationery.entity.HasId;
import my.epam.stationery.model.Assign;
import my.epam.stationery.model.Employee;
import my.epam.stationery.model.Stationery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StationeryManager {

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

    public void assignStationery(Stationery stationery, Employee employee) {
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

    public void unassignStationery(Stationery stationery) {
        if (stationery.getId() == null) throw new IllegalArgumentException("Stationery not found");

        Long stId = stationery.getId();
        Assign exists = assignDao.getAssignByStationeryId(stId);
        if (exists == null) {
            throw new IllegalArgumentException("Stationery with id = " + stId + " already assigned for employee " + exists.getEmployeeId());
        } else {
            assignDao.remove(exists);
        }
    }

    public void assignStationeries(Stationery[] stationeries, Employee employee) {
        for(Stationery st : stationeries){
            assignStationery(st, employee);
        }
    }

    public void moveStationery(Stationery stationery, Employee newEmployee) {
        unassignStationery(stationery);
        assignStationery(stationery, newEmployee);
    }

    public Employee getEmployeeOfStationery(Stationery st){
        if(st.getId() == null) throw new IllegalArgumentException("Record not found.");
        Long emplId = assignDao.getAssignByStationeryId(st.getId()).getEmployeeId();
        return emplDao.getById(emplId);
    }

    public Employee saveEmployee(Employee employee){
        return emplDao.saveOrUpdateAndReturn(employee);
    }

    public List<Employee> findEmployeeBy(Map<String, String> valMap){
        return emplDao.findBy(valMap);
    }

    public void removeEmployee(Employee employee){
        for(Assign assign : assignDao.getAssignsForEmployeeId(employee.getId())){
            assignDao.remove(assign);
        }
        emplDao.remove(employee);
    }

    public void removeEmployee(Long id){
        for(Assign assign : assignDao.getAssignsForEmployeeId(id)){
            assignDao.remove(assign);
        }
        emplDao.remove(id);
    }

    public List<Employee> getEmployees(){
        return emplDao.getAll();
    }

    public Stationery saveStationery(Stationery stationery){
        return stDao.saveOrUpdateAndReturn(stationery);
    }

    public List<Stationery> findStationeryBy(Map<String, String> valMap){
        return stDao.findBy(valMap);
    }

    public void removeStationery(Stationery stationery) {
        Assign a = assignDao.getAssignByStationeryId(stationery.getId());
        assignDao.remove(a);
        stDao.remove(stationery);
    }

    public void removeStationery(Long id){
        Assign a = assignDao.getAssignByStationeryId(id);
        assignDao.remove(a);
        stDao.remove(id);
    }

    public List<Stationery> getStationeries(){
        return stDao.getAll();
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
}
