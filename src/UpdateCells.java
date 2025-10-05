public interface UpdateCells {

    void updateCells(int m0, int n0, int m1, int n1);
    void notifyCellOfPmPresence(int m, int n, boolean present);
    void notifyCellOfGhostPresence(GhostNames ghostName, int m, int n, boolean present);

}
