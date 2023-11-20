#include <iostream>
#include <vector>

using namespace std;

class BullyElection {
public:
    BullyElection(int numProcesses, int processId) : numProcesses(numProcesses), processId(processId) {}

    void runElection() {
        cout << "Process " << processId << " initiates the election in the Bully algorithm." << endl;

        int highestPriority = processId;
        for (int i = processId + 1; i < numProcesses; ++i) {
            sendElectionMessage(i);
        }

        int coordinator = processId;

        for (int i = processId + 1; i < numProcesses; ++i) {
            receiveElectionMessage(i, coordinator);
        }

        if (coordinator == processId) {
            cout << "Process " << processId << " becomes the coordinator." << endl;
        } else {
            cout << "Process " << processId << " recognizes Process " << coordinator << " as the coordinator." << endl;
        }
    }

private:
    void sendElectionMessage(int destProcess) {
        cout << "Process " << processId << " sends an election message to Process " << destProcess << "." << endl;
    }

    void receiveElectionMessage(int senderProcess, int &coordinator) {
        cout << "Process " << processId << " waits to receive an election message from Process " << senderProcess << "." << endl;
        // Simulate receiving an election message and updating coordinator
        if (senderProcess > coordinator) {
            coordinator = senderProcess;
        }
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

    BullyElection bullyElection(numProcesses, processId);
    bullyElection.runElection();

    return 0;
}
