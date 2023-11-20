#include <iostream>
#include <vector>

using namespace std;

class RingElection {
public:
    RingElection(int numProcesses, int processId) : numProcesses(numProcesses), processId(processId) {}

    void runElection() {
        cout << "Process " << processId << " initiates the election in the Ring." << endl;

        int coordinator = processId;
        for (int i = 0; i < numProcesses - 1; ++i) {
            sendElectionMessage((processId + i + 1) % numProcesses);
        }

        cout << "Process " << processId << " waits for the coordinator to be elected." << endl;

        while (coordinator == processId) {
            receiveElectionMessage(coordinator);
        }

        cout << "Process " << processId << " declares Process " << coordinator << " as the coordinator." << endl;
    }

private:
    void sendElectionMessage(int destProcess) {
        cout << "Process " << processId << " sends an election message to Process " << destProcess << "." << endl;
    }

    void receiveElectionMessage(int &coordinator) {
        cout << "Process " << processId << " waits to receive an election message." << endl;
        // Simulate receiving an election message and updating coordinator
        coordinator = (coordinator + 1) % numProcesses;
    }

    int numProcesses;
    int processId;
};

int main() {
    int numProcesses, processId;
    cout << "Enter the number of processes: ";
    cin >> numProcesses;
    cout << "Enter the process ID (0 to " << numProcesses - 1 << "): ";
    cin >> processId;

    RingElection ringElection(numProcesses, processId);
    ringElection.runElection();

    return 0;
}
