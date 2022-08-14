package Data.Repositories;

import java.util.List;

public interface ExcelRepository<Model> {

    public List<Model> getAllCreditsWithLate();

    // Here goes deletion method...

}
