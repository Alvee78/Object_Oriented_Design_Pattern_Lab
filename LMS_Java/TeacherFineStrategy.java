// ======================================================================
//  Strategy Pattern — Concrete Strategy B
//  TeacherFineStrategy: 3 BDT per overdue day (reduced academic rate)
// ======================================================================

/**
 * Strategy Pattern — Concrete Strategy B
 * Teacher fine: 3 BDT per overdue day (reduced rate for faculty).
 */
public class TeacherFineStrategy implements FineStrategy {

    private static final double RATE_PER_DAY = 3.0; // BDT

    @Override
    public double calculateFine(int overdueDays) {
        return overdueDays * RATE_PER_DAY;
    }

    @Override
    public String getDescription() {
        return "Teacher Fine Strategy: " + RATE_PER_DAY + " BDT/day (reduced faculty rate)";
    }
}
