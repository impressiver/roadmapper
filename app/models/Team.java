package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Team extends Model {
    public static Model.Finder<Long, Team> find = new Model.Finder<>(Long.class, Team.class);

    @Id
    public Long id;

    @Constraints.Required
    public String name;

    @Constraints.Required
    @Constraints.Max(100)
    @Constraints.Min(0)
    public int utilization;

    public Map<Quarter, StaffSummary> quarterStaffSummary = new HashMap<>();
}
