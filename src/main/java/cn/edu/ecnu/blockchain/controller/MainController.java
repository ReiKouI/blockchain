package cn.edu.ecnu.blockchain.controller;

import cn.edu.ecnu.blockchain.agent.Agent;
import cn.edu.ecnu.blockchain.agent.AgentManager;
import cn.edu.ecnu.blockchain.agent.Block;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(path = "/agent")
public class MainController {

    private static AgentManager agentManager = new AgentManager();

    @RequestMapping(method = GET)
    public Agent getAgent(@RequestParam("name") String name) {
        return agentManager.getAgentByName(name);
    }

    @RequestMapping(method = DELETE)
    public void deleteAgent(@RequestParam("name") String name) {
        agentManager.deleteAgent(name);
    }

    @RequestMapping(method = POST, params = {"name", "port"})
    public Agent addAgent(@RequestParam("name") String name, @RequestParam("port") int port) {
        return agentManager.addAgent(name, port);
    }

    @RequestMapping(path = "all", method = GET)
    public List<Agent> getAllAgents() {
        return agentManager.getAllAgents();
    }

    @RequestMapping(path = "all", method = DELETE)
    public void deleteAllAgents() {
        agentManager.deleteAllAgents();
    }

    @RequestMapping(method = POST, path = "mine")
    public Block createBlock(@RequestParam(value = "agent") final String name) {
        return agentManager.createBlock(name);
    }

    @RequestMapping(method = POST, path = "transaction")
    public Block createBlock(@RequestParam(value = "agent") final String miner,
                             @RequestParam(value = "sender") final String sender,
                             @RequestParam(value = "receiver") final String receiver,
                             @RequestParam(value = "value") final Double value


    ) {
        return agentManager.createTransaction(miner, sender, receiver, value);
    }

    @RequestMapping(method = POST, path = "blockchain")
    public List<Block> getBlockChain(@RequestParam(value = "name") final String name


    ) {
        return agentManager.getAgentByName(name).getBlockchain();
    }


}
