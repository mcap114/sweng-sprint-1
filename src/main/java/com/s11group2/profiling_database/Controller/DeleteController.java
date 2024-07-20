package com.s11group2.profiling_database.Controller;

import com.s11group2.profiling_database.Model.DatabaseManager;
import com.s11group2.profiling_database.Model.Household;
import com.s11group2.profiling_database.Model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.SQLException;

@Controller
public class DeleteController {

    private final DatabaseManager databaseManager;

    @Autowired
    public DeleteController(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @GetMapping("/deleteunit/{buildingNum}/{unitNum}")
    public String delete(@PathVariable int buildingNum, @PathVariable int unitNum, Model model) throws SQLException {

        databaseManager.deleteHousehold(buildingNum, unitNum);

        return "viewunits";
    }
}
