package org.application.resources;

import lombok.NoArgsConstructor;
import org.application.models.custom.RequestRecord;
import org.application.repositories.custom.CustomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@NoArgsConstructor
@RequestMapping("/records")
public class RecordController {

    private CustomRepo<RequestRecord,Long> requestRecordRepo;

    @Autowired
    public RecordController(CustomRepo<RequestRecord,Long> requestRecordRepo) {
        this.requestRecordRepo = requestRecordRepo;
    }

    @GetMapping
    public List<RequestRecord> getRecords() throws SQLException {
        return requestRecordRepo.getAll();
    }

    @GetMapping("/{id}")
    public RequestRecord getRecord(@PathVariable("id") long id) throws SQLException {
        return requestRecordRepo.get(id);
    }

}
