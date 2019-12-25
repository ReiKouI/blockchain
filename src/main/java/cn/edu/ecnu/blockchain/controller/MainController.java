package cn.edu.ecnu.blockchain.controller;

import cn.edu.ecnu.blockchain.agent.Agent;
import cn.edu.ecnu.blockchain.agent.AgentManager;
import cn.edu.ecnu.blockchain.agent.Transaction;
import cn.edu.ecnu.blockchain.result.Result;
import cn.edu.ecnu.blockchain.util.ResultUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping(path = "/agent")
public class MainController {

    private static AgentManager agentManager = new AgentManager();

    @RequestMapping(path = "test", method = GET)
    public void test() {
        agentManager.addAgent("A1", 3001);
        agentManager.addAgent("A2", 3002);
        agentManager.addAgent("A3", 3003);
        agentManager.addAgent("A4", 3004);
        agentManager.addAgent("A5", 3005);
        agentManager.createBlock("A1");
        agentManager.createBlock("A2");
        agentManager.createBlock("A3");
        agentManager.createBlock("A4");
        agentManager.createBlock("A5");
        final Transaction transaction1 = agentManager.createTransaction("A1", "A2", 4.2);
        final Transaction transaction2 = agentManager.createTransaction("A3", "A2", 1.2);
        agentManager.createTransaction("A3", "A4", 3.3);
        agentManager.createTransaction("A5", "A4", 2.1);
        agentManager.createTransaction("A5", "A1", 2.1);
        agentManager.createBlock("A5", transaction1.getSignature());
        agentManager.createBlock("A4", transaction2.getSignature());
    }

    @RequestMapping(method = GET)
    public Result getAgent(@RequestParam("name") String name) {
        return ResultUtil.success(agentManager.getAgentByName(name));
    }

    @RequestMapping(method = DELETE)
    public void deleteAgent(@RequestParam("name") String name) {
        agentManager.deleteAgent(name);
    }

    @RequestMapping(method = POST, params = {"name", "port"})
    public Result addAgent(@RequestParam("name") String name, @RequestParam("port") int port) {
        return ResultUtil.success(agentManager.addAgent(name, port));
    }

    @RequestMapping(path = "all", method = GET)
    public Result getAllAgents() {
        return ResultUtil.success(agentManager.getAllAgents());
    }

    @RequestMapping(path = "all", method = DELETE)
    public void deleteAllAgents() {
        agentManager.deleteAllAgents();
    }

    @RequestMapping(method = POST, path = "blank")
    public Result createBlock(@RequestParam(value = "name") final String name) {
        return ResultUtil.success(agentManager.createBlock(name));
    }

    @RequestMapping(method = POST, path = "transactions")
    public Result getTransactions(@RequestParam(value = "name") final String name
    ) {
        return ResultUtil.success(agentManager.getAgentByName(name).getTransactions());
    }

    @RequestMapping(method = POST, path = "transaction")
    public Result doTransaction(
                             @RequestParam(value = "sender") final String sender,
                             @RequestParam(value = "receiver") final String receiver,
                             @RequestParam(value = "value") final Double value
    ) {
        return ResultUtil.success(agentManager.createTransaction(sender, receiver, value));
    }

    @RequestMapping(method = POST, path = "mine")
    public Result mine(@RequestParam(value = "miner") final String miner,@RequestParam(value = "signature") final String signature) {
        return ResultUtil.success(agentManager.createBlock(miner, signature));
    }


    @RequestMapping(method = POST, path = "blockchain")
    public Result getBlockChain(@RequestParam(value = "name") final String name
    ) {
        return ResultUtil.success(agentManager.getAgentByName(name).getBlockchain());
    }

    @RequestMapping(method = POST, path = "balance")
    public Result getBalance(@RequestParam(value = "name") final String name
    ) {
        final Agent agent = agentManager.getAgentByName(name);
        return ResultUtil.success(agent.getAvailableAccount());
    }
    
}
