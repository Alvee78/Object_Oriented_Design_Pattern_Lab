// ======================================================================
//  Strategy Pattern — FineStrategy Interface
//  Different user types (Student, Teacher) have different fine rates.
// ======================================================================

/**
 * Strategy Pattern — Strategy Interface
 * Encapsulates a fine calculation algorithm.
 * Concrete strategies: StudentFineStrategy, TeacherFineStrategy
 */
public interface FineStrategy {
    /**
     * Calculate the fine amount based on overdue days.
     * @param overdueDays number of days past due date
     * @return fine amount in BDT
     */
    double calculateFine(int overdueDays);

    /**
     * Human-readable description of this strategy.
     */
    String getDescription();
}
