import com.sun.tools.attach.*;

import java.io.IOException;
import java.util.List;

public class AttachAgent {
    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        List<VirtualMachineDescriptor> vms = VirtualMachine.list();
        for (VirtualMachineDescriptor vm : vms) {
            if (vm.displayName() == null || vm.displayName().trim().equals("") || !vm.displayName().contains("SimplestSpringBootApplication")) {
                continue;
            }
            System.out.print(vm.id() + ":");
            System.out.println(vm.displayName());
            VirtualMachine attach = VirtualMachine.attach(vm.id());
            attach.loadAgent("C:\\Users\\nilknow\\IdeaProjects\\nilknow-blog\\nilknow-hotreload\\target\\nilknow-hotreload-1.0-SNAPSHOT-jar-with-dependencies.jar");
            attach.detach();
        }
//        long pid = ManagementFactory.getRuntimeMXBean().getPid();
//        VirtualMachine vm = VirtualMachine.attach(String.valueOf(pid));
//        System.out.println(vm.id());
    }
}
