package my.epam.stationery.dao;

import my.epam.stationery.model.Assign;
import my.epam.stationery.model.StringParser;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AssignDao extends FiledDao<Assign>{
    public AssignDao(File dataFile, StringParser<Assign> parser) {
        super(dataFile, parser);
    }

    @Override
    public List<Assign> getAll() {
        return super.getAll();
    }

    @Override
    public Assign getById(long id) {
        return super.getById(id);
    }

    @Override
    public void remove(long id) {
        super.remove(id);
    }

    @Override
    public void remove(Assign obj) {
        super.remove(obj);
    }

    @Override
    public long saveOrUpdate(Assign obj) {
        return super.saveOrUpdate(obj);
    }

    public Assign[] getAssignsForEmployeeId(Long emplId){
        return getAll().stream()
                .filter((a)->a.getEmployeeId().equals(emplId))
                .collect(Collectors.toList())
                .toArray(new Assign[0]);
    }

    public Assign getAssignByStationeryId(Long stId){
        List<Assign> result = getAll().stream()
                .filter((a)->a.getStationeryId().equals(stId))
                .collect(Collectors.toList());
        if(result.size() != 0) return result.get(0);
        else return null;
    }

}
