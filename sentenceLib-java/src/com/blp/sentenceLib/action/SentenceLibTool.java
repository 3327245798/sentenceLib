package com.blp.sentenceLib.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.blp.sentenceLib.AppConfig;
import com.blp.sentenceLib.constant.DraftReleaseType;
import com.blp.sentenceLib.constant.DraftTypeConstant;
import com.blp.sentenceLib.entity.*;
import com.blp.sentenceLib.entity.vo.DraftScriptVo;
import com.blp.sentenceLib.remote.service.CoreRemoteService;
import com.blp.sentenceLib.remote.service.DispatcherService;
import com.blp.sentenceLib.remote.service.IOrgToolRemoteService;
import com.blp.sentenceLib.service.*;
import com.blp.sentenceLib.util.JsonUtils;
import com.blp.sentenceLib.util.R;
import com.fy.basejar.tool.ActionToolBase;

import com.fy.toolhelper.util.RequestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * BLP Apis
 */
public class SentenceLibTool extends ActionToolBase {

    public final static ApplicationContext IOC = AppConfig.initApplicationContext();

    @Action
    public String init1(HttpServletRequest request) throws Exception {
        System.out.println("测试启动");
        return "init ok";
    }

    /////重新设计后的固件表，blp_firmware,他的参数存放在blp_param里面/////////////////////////
    //根据工具id获取所有的部件
    @Action
    public R getFirmwaresByToolId(HttpServletRequest request) throws Exception {
        FirmwareService firmwareService= IOC.getBean(FirmwareService.class);
        Long toolId = RequestUtils.getLongParameter(request, "toolId");
        List<Firmware> firmwareList = firmwareService.getFirmwaresByToolId(toolId);
        Map<String, Object> map = new HashMap<>();
        map.put("firmware", firmwareList);
        return R.ok().put("rows", map).message("根据工具id获取该工具的所有部件，查询成功");
    }
    //根据固件id和type获取输入或输出参数
    @Action
    public R getParamsByFirmwareIdAndType(HttpServletRequest request) throws Exception {
        NewParamService paramService=IOC.getBean(NewParamService.class);
        Long firmwareId = RequestUtils.getLongParameter(request, "firmwareId");
        int type=RequestUtils.getIntegerParameter(request, "type");
        List<NewParam> paramList=paramService.getParamstByFirmwareIdAndType(firmwareId,type);
        Map<String, Object> map = new HashMap<>();
        map.put("paramList", paramList);
        return R.ok().put("rows", map).message("根据固件id和输入输出参数类型获取输入或输出参数，获取成功");
    }
    /*根据词汇id获取firmware表中的工具id,工具名称，部件id,部件名称及参数表中的参数信息 ，
   输入输出参数类型，是否必填，参数中英文名称，父参数id*/
    @Action
    public R getToolInfoFirmwareInfoParamInfoByWordId(HttpServletRequest request) throws Exception {
        FirmwareService firmwareService= IOC.getBean(FirmwareService.class);
        Long wordId = RequestUtils.getLongParameter(request, "wordId");
        Firmware firmware = firmwareService.getToolInfoFirmwareInfoParamInfoByWordId(wordId);

        Map<String, Object> map = new HashMap<>();
        map.put("firmware", firmware);
        return R.ok().put("rows", map).message("根据词汇id获取该工具信息及参数信息，获取成功");
    }
    /*根据工具id和部件action与获取输入或输出参数列表，inouttype=1时获取输入参数列表*/
    @Action
    public R getInputParamsByToolIdAndApiAction(HttpServletRequest request) throws Exception {
        NewParamService paramService=IOC.getBean(NewParamService.class);
        Long toolId = RequestUtils.getLongParameter(request, "toolId");
        String apiAction=RequestUtils.getStringParameter(request,"apiAction");
        List paramList=paramService.getInputParamsByToolIdAndApiAction(toolId,apiAction,1);
        return R.ok().put("rows", paramList).message("根据工具id和部件action获取输入参数列表，获取成功");

    }


    ///////////////////////////////

    /**
     * 应用案例相关的帮区语境
     */
    @Action
    public R getRelationBandsEnvironment(HttpServletRequest request) throws Exception {
        BandsAndOrganizationEnvironmentService environmentService =
            IOC.getBean(BandsAndOrganizationEnvironmentService.class);
        List<String> bandList = RequestUtils.getJsonArrayParameter(request, "bandList");

        List<Map<String, Object>> maps = new ArrayList<>(bandList.size() + 1);

        for (String band : bandList) {
            Long bandId = Long.parseLong(band);
            Map<String, Object> bandEnvironment = environmentService.getBandEnvironment(bandId, bandId);
            maps.add(bandEnvironment);
        }

        return R.ok().put("rows", maps).message("返回应用案例相关的帮区语境成功");
    }
    //获取帮区里的所有工具

    @Action
    public R getToolsInBand(HttpServletRequest request) {
        CoreRemoteService coreRemoteService = IOC.getBean(CoreRemoteService.class);
        JSONObject jsonObject = null;
        try {
            jsonObject=coreRemoteService.getToolsInBand(getBandID());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return R.ok().put("tools",jsonObject).message("获取该帮区下的所有工具，获取成功");

    }
    /*获取当前用户的角色（在不同的机构可能角色不同，角色是需要设置的）*/
    @Action
    public R getUserRoles(HttpServletRequest request) {
        CoreRemoteService coreRemoteService = IOC.getBean(CoreRemoteService.class);
        JSONObject jsonObject=null;
        try {
             jsonObject = coreRemoteService.getUserRolesInBand(getBandID(), getUserID());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.ok().put("userRoles",jsonObject).message("获取该用户的角色，获取成功");
    }
    /**
     * 调用系统工具进行发布
     */
    @Action
    public R createShareInCore(HttpServletRequest request) {
        String bandId = RequestUtils.getStringParameter(request, "bandId");
        String roleName = RequestUtils.getStringParameter(request, "roleName");
        String title = RequestUtils.getStringParameter(request, "title");
        String description = RequestUtils.getStringParameter(request, "description");
        DispatcherService dispatcherService = IOC.getBean(DispatcherService.class);
        JSONObject result = null;
        try {
            result = dispatcherService.createShareInCore(bandId, roleName, title, description);
        } catch (Exception e) {
            R.error().message("创建发布失败");
        }
        return R.ok().put(result).message("创建发布成功");
    }

    /**
     * 根据工具名字获取工具id
     */
    @Action
    @Deprecated
    public R getToolInfo(HttpServletRequest request) {
        String toolName = RequestUtils.getStringParameter(request, "toolName");
        String bandId = RequestUtils.getStringParameter(request, "bandId");
        CoreRemoteService coreRemoteService = IOC.getBean(CoreRemoteService.class);
        JSONObject jsonObject = null;
        try {
            jsonObject = coreRemoteService.getToolByToolName(toolName, bandId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // toolID -> 7600023627
        JSONArray rows = jsonObject.getJSONArray("rows");
        String objID = (String)rows.getJSONObject(0).get("objID");
        String organizationID = (String)rows.getJSONObject(0).get("organizationID");
        String realObjID = (String)rows.getJSONObject(0).get("realObjID");
        HashMap<String, String> tool = new HashMap<>();
        tool.put("objID", objID);
        tool.put("organizationID", organizationID);
        tool.put("realObjID", realObjID);
        return R.ok().put("tool", tool).message("返回" + toolName + "的相关信息成功");
    }

    /**
     * 获取词汇与句型库
     * 
     */
/*    @Action
    public R getWordAndSentenceEnvironment(HttpServletRequest request) throws Exception {

        List<String> bandList = RequestUtils.getJsonArrayParameter(request, "bandList");
        Integer type = RequestUtils.getIntegerParameter(request, "type");

        UnitService unitService = IOC.getBean(UnitService.class);
        List<Unit> unitList = unitService.getAllUnits();

        SentenceService sentenceService = IOC.getBean(SentenceService.class);
        List<Sentence> sentenceList = sentenceService.getAllSentencesByType(type);

        Map<String, Object> map = new HashMap<>();

        List<Map<String, Object>> wordList = new ArrayList<>(bandList.size() + 1);

        BandsAndOrganizationEnvironmentService environmentService =
            IOC.getBean(BandsAndOrganizationEnvironmentService.class);

        List<Map<String, Object>> actionList = new ArrayList<>();
        for (Unit unit : unitList) {
            Map<String, Object> unitMap = new HashMap<>();
            unitMap.put("actionName", unit.getName());
            // 便于前端处理
            unitMap.put("actionId", unit.getAction());

            List<Map<String, String>> paramsList = new ArrayList();
            String paramsStr = unit.getParams();
            Map<String, String> paramsMap = (Map<String, String>)JSON.parse(paramsStr);
            for (String key : paramsMap.keySet()) {
                Map<String, String> pair = new HashMap();
                pair.put("paramName", key);
                pair.put("paramField", paramsMap.get(key));
                paramsList.add(pair);
            }
            unitMap.put("params", paramsList);
            unitMap.put("paramsNum", paramsList.size());

            List<Map<String, String>> resultList = new ArrayList();
            // 执行结果字段序列化内容 {"招标结束时间":"result.data.time"}
            String resultStr = unit.getResult();
            Map<String, String> resultMap = (Map<String, String>)JSON.parse(resultStr);
            for (String key : resultMap.keySet()) {
                Map<String, String> pair = new HashMap();
                pair.put("resultName", key);
                pair.put("resultField", resultMap.get(key));
                resultList.add(pair);
            }
            unitMap.put("result", resultList);
            unitMap.put("resultNum", resultList.size());

            actionList.add(unitMap);
        }

        for (String band : bandList) {
            Long bandId = Long.parseLong(band);
            Map<String, Object> bandEnvironment = environmentService.getBandEnvironment(bandId, bandId);
            bandEnvironment.put("toolActions", actionList);
            wordList.add(bandEnvironment);
        }

        map.put("word", wordList);
        map.put("sentence", sentenceList);

        return R.ok().put("rows", map).message("返回词汇与句型库成功");
    }*/

    /*** 获取词汇与句型库*/

    @Action
    public R getWordAndSentenceEnvironment(HttpServletRequest request) throws Exception {

        List<String> bandList = RequestUtils.getJsonArrayParameter(request, "bandList");
        Integer type = RequestUtils.getIntegerParameter(request, "type");

        UnitService unitService = IOC.getBean(UnitService.class);
        List<Unit> unitList = unitService.getAllUnits();

        SentenceService sentenceService = IOC.getBean(SentenceService.class);
        List<Sentence> sentenceList = sentenceService.getAllSentencesByType(type);

        Map<String, Object> map = new HashMap<>();

        List<Map<String, Object>> wordList = new ArrayList<>(bandList.size() + 1);

        BandsAndOrganizationEnvironmentService environmentService =
                IOC.getBean(BandsAndOrganizationEnvironmentService.class);

        List<Map<String, Object>> actionList = new ArrayList<>();
        for (Unit unit : unitList) {
            Map<String, Object> unitMap = new HashMap<>();
            unitMap.put("actionName", unit.getName());
            // 便于前端处理
            unitMap.put("actionId", unit.getAction());

            List<Map<String, String>> paramsList = new ArrayList();
            String paramsStr = unit.getParams();
            Map<String, String> paramsMap = (Map<String, String>)JSON.parse(paramsStr);
            for (String key : paramsMap.keySet()) {
                Map<String, String> pair = new HashMap();
                pair.put("paramName", key);
                pair.put("paramField", paramsMap.get(key));
                paramsList.add(pair);
            }
            unitMap.put("params", paramsList);
            unitMap.put("paramsNum", paramsList.size());

            List<Map<String, String>> resultList = new ArrayList();
            // 执行结果字段序列化内容 {"招标结束时间":"result.data.time"}
            String resultStr = unit.getResult();
            Map<String, String> resultMap = (Map<String, String>)JSON.parse(resultStr);
            for (String key : resultMap.keySet()) {
                Map<String, String> pair = new HashMap();
                pair.put("resultName", key);
                pair.put("resultField", resultMap.get(key));
                resultList.add(pair);
            }
            unitMap.put("result", resultList);
            unitMap.put("resultNum", resultList.size());

            actionList.add(unitMap);
        }
        //随便 构造一个variableList  wz
        List<Map<String, Object>> variableList = new ArrayList<>();
        Map<String, Object> variableMap1 = new HashMap<>();
        variableMap1.put("variableId","动态变量");
        variableMap1.put("variableName","工具执行结果");
        variableMap1.put("variableType","动态变量");
        variableList.add(variableMap1);

        Map<String, Object> variableMap2 = new HashMap<>();
        variableMap2.put("variableId","静态变量");
        variableMap2.put("variableName","截止时间");
        variableMap2.put("variableType","静态变量");
        variableList.add(variableMap2);


        for (String band : bandList) {
            Long bandId = Long.parseLong(band);
            Map<String, Object> bandEnvironment = environmentService.getBandEnvironment(bandId, bandId);
            bandEnvironment.put("toolActions", actionList);
            bandEnvironment.put("variables", variableList);//wz修改
            wordList.add(bandEnvironment);
        }

        map.put("word", wordList);
        map.put("sentence", sentenceList);

        return R.ok().put("rows", map).message("返回词汇与句型库成功");
    }
    /**
     * 根据帮区id获取帮区路径wz*/
    @Action
    public R getBandsPath(HttpServletRequest request) throws Exception{
        CoreRemoteService coreRemoteService=IOC.getBean(CoreRemoteService.class);
        String bandId = RequestUtils.getStringParameter(request, "bandId");
        JSONObject bandTree=coreRemoteService.getBandsPath(Long.valueOf(bandId));

        return R.ok().put("rows", bandTree).message("根据当前帮区id获取帮区路径");

    }
    /**
     *根据帮区id获取 帮区工具中的中词汇wz*/
    @Action
    public R getOnlyWords (HttpServletRequest request) throws Exception {
        CoreRemoteService coreRemoteService=IOC.getBean(CoreRemoteService.class);
        String bandId = RequestUtils.getStringParameter(request, "bandId");
        JSONObject bandVariable=coreRemoteService.getToolsInBand(Long.valueOf(bandId));

        return R.ok().put("bandVariableArray", bandVariable).message("根据当前帮区id获取帮区工具中的词汇");
    }
    /**仅仅获取句型,获取的是所有类型的句型，类型分为标准型和非标准型wz*/
    @Action
    public R getAllSentence (HttpServletRequest request) throws Exception {
       // Integer type = RequestUtils.getIntegerParameter(request, "type");
        SentenceService sentenceService = IOC.getBean(SentenceService.class);
        List<Sentence> sentenceList = sentenceService.getAllSentences();
        Map<String, Object> map = new HashMap<>();
        map.put("sentence", sentenceList);
        return R.ok().put("rows", map).message("返回句型库成功");

    }
    /////////////////////////////////////////////////////////////////////////////////////////
    /*根据用户输入关键字搜索句型，返回给前端，前端再根据句型name进行分类处理，展示*/
    /*获取根据句型id获取句型信息，主要是为了获取固件代码属性*/
    @Action
    public R getSentenceById(HttpServletRequest request) throws Exception {
        SentenceService sentenceService = IOC.getBean(SentenceService.class);
        Long sentenceId =RequestUtils.getLongParameter(request, "sentenceId");
        Sentence sentence=sentenceService.getSentenceById(sentenceId);
        return R.ok().put("rows", sentence).message("根据句型id获取句型信息，成功");
    }
    @Action
    public R  getAllSentencesByuserInputKeyword(HttpServletRequest request) throws Exception {
        SentenceService sentenceService = IOC.getBean(SentenceService.class);
        String userInputKeyword = RequestUtils.getStringParameter(request, "userInputKeyword");
        List<Sentence> sentenceList = sentenceService.getAllSentencesByuserInputKeyword(userInputKeyword);
        Map<String, Object> map = new HashMap<>();
        map.put("sentence", sentenceList);
        return R.ok().put("rows", map).message("根据用户输入关键字搜索句型，成功");
    }
    /*根据创建者姓名获取句型*/
    @Action
    public R getAllSentenceByCreatorName (HttpServletRequest request) throws Exception {

        SentenceService sentenceService = IOC.getBean(SentenceService.class);
        String creatorName = RequestUtils.getStringParameter(request, "creatorName");
        List<Sentence> sentenceList = sentenceService.getAllSentencesByCreatorName(creatorName);
        Map<String, Object> map = new HashMap<>();
        map.put("sentence", sentenceList);
        return R.ok().put("rows", map).message("根据创建者姓名查询句型，查询成功");

    }
    /*根据句型状态获取句型*/
    @Action
    public R getAllSentenceByStatus (HttpServletRequest request) throws Exception{
        SentenceService sentenceService = IOC.getBean(SentenceService.class);
        Integer status=RequestUtils.getIntegerParameter(request,"status");
        List<Sentence> sentenceList =sentenceService.getAllSentenceByStatus(status);
        Map<String, Object> map = new HashMap<>();
        map.put("sentence", sentenceList);
        return R.ok().put("rows", map).message("根据句型状态查询句型，查询成功");
    }
    /*获取处于审核中的句型，为前台帮语审核员服务*/
    @Action
    public R getAllSentenceInChecking (HttpServletRequest request) throws Exception{
        SentenceService sentenceService = IOC.getBean(SentenceService.class);
        List<Sentence> sentenceList =sentenceService.getAllSentenceInChecking();
        Map<String, Object> map = new HashMap<>();
        map.put("sentence", sentenceList);
        return R.ok().put("rows", map).message("获取处于审核中的句型，获取成功");
    }
    /*获取当天创建的句型*/
    @Action
    public R getAllSentenceInDay (HttpServletRequest request) throws Exception{
        SentenceService sentenceService = IOC.getBean(SentenceService.class);
        List<Sentence> sentenceList =sentenceService.getAllSentenceInDay();
        Map<String, Object> map = new HashMap<>();
        map.put("sentence", sentenceList);
        return R.ok().put("rows", map).message("获取当天创建的句型，获取成功");
    }
    /*获取一周之内创建的句型*/
    @Action
    public R  getAllSentenceInWeek (HttpServletRequest request) throws Exception{
        SentenceService sentenceService = IOC.getBean(SentenceService.class);
        List<Sentence> sentenceList =sentenceService.getAllSentenceInWeek();
        Map<String, Object> map = new HashMap<>();
        map.put("sentence", sentenceList);
        return R.ok().put("rows", map).message("获取一周内创建的句型，获取成功");
    }
    /*获取一个月之内创建的句型*/
    @Action
    public R getAllSentenceInMonth (HttpServletRequest request) throws Exception{
        SentenceService sentenceService = IOC.getBean(SentenceService.class);
        List<Sentence> sentenceList =sentenceService.getAllSentenceInMonth();
        Map<String, Object> map = new HashMap<>();
        map.put("sentence", sentenceList);
        return R.ok().put("rows", map).message("获取一个月内创建的句型，获取成功");
    }
    /**添加句型wz*/
    @Action
    public R addSentence (HttpServletRequest request) throws Exception {
        SentenceService sentenceService=IOC.getBean(SentenceService.class);
        JSONObject sentenceJsonObject = JSONObject.parseObject(RequestUtils.getStringParameter(request, "sentence"));
        Sentence sentence= JsonUtils.getJavaObject(sentenceJsonObject, Sentence.class);
/*添加这4行就可以实现不用前端手动输入，后端自动往数据库里面插入数据*/
        sentence.setCreatorId(getUserID());
        sentence.setCreatorName(getUserName());
        sentence.setCreateTime(new Date());
        sentence.setUpdateTime(new Date());
        /*当用户新增一个句型时，默认是审核中状态*/
        sentence.setStatus(0);

        sentenceService.insertSentence(sentence);
        return R.ok().message("添加句型成功").put("rows",sentence);
    }

    /**删除句型wz*/
    @Action
    public R deleteSentence(HttpServletRequest request) throws Exception {
        SentenceService sentenceService=IOC.getBean(SentenceService.class);
        JSONObject sentenceJsonObject = JSONObject.parseObject(RequestUtils.getStringParameter(request, "sentence"));
        Sentence sentence=JsonUtils.getJavaObject(sentenceJsonObject, Sentence.class);
        int i=sentenceService.deleteSentence(sentence);
        return i == 0 ? R.error().message("删除句型失败") : R.ok().message("删除句型成功");

    }
    /**修改句型wz*/
    @Action
    public R updateSentence(HttpServletRequest request) throws Exception {
        SentenceService sentenceService=IOC.getBean(SentenceService.class);
        JSONObject sentenceJsonObject = JSONObject.parseObject(RequestUtils.getStringParameter(request, "sentence"));
        Sentence sentence=JsonUtils.getJavaObject(sentenceJsonObject, Sentence.class);
        sentence.setUpdateTime(new Date());
        int i=sentenceService.updateSentence(sentence);
        return i == 0 ? R.error().message("修改句型失败") : R.ok().message("修改句型成功");
    }
    /////////////////////////////////////////////////////////////////////////////////////////
        /**
         * 根据当前帮区id获取所在机构所有帮区
         */
    @Action
    public R getBandList(HttpServletRequest request) throws Exception {
        BandsAndOrganizationEnvironmentService environmentService =
            IOC.getBean(BandsAndOrganizationEnvironmentService.class);
        String bandId = RequestUtils.getStringParameter(request, "bandId");
        Map<String, Object> bandList = environmentService.getOrganizationAndBands(Long.valueOf(bandId));
        return R.ok().put("rows", bandList).message("根据当前帮区id获取所在机构所有帮区");
    }
    //-----------------------------------------------------------------------------
    /**公共词汇部分wz*/
    /*创建新词汇wz*/
    @Action
    public R addPublicWord (HttpServletRequest request) throws Exception {
        WordService wordService=IOC.getBean(WordService.class);
        JSONObject wordJsonObject = JSONObject.parseObject(RequestUtils.getStringParameter(request, "word"));
        Word word= JsonUtils.getJavaObject(wordJsonObject, Word.class);

        word.setCreatorId(getUserID());
        word.setCreatorName(getUserName());
        word.setCreateTime(new Date());
        word.setUpdateTime(new Date());
        /*当用户新增一个词汇时，默认是审核中状态*/
        word.setStatus(1);
        wordService.insertWord(word);
        return R.ok().message("添加词汇成功").result("成功");
    }
    /**修改公共词汇wz*/
    @Action
    public R updateWord (HttpServletRequest request) throws Exception{
        WordService wordService=IOC.getBean(WordService.class);
        JSONObject wordJsonObject = JSONObject.parseObject(RequestUtils.getStringParameter(request, "word"));
        Word word=JsonUtils.getJavaObject(wordJsonObject, Word.class);
        word.setUpdateTime(new Date());
        int i=wordService.updateWord(word);
        return i == 0 ? R.error().message("修改词汇失败") : R.ok().message("修改词汇成功").put("word",word);

    }
    /**删除词汇wz*/
    @Action
    public R deleteWord(HttpServletRequest request) throws Exception {
        WordService wordService=IOC.getBean(WordService.class);
        JSONObject wordJsonObject = JSONObject.parseObject(RequestUtils.getStringParameter(request, "word"));
        Word word=JsonUtils.getJavaObject(wordJsonObject, Word.class);
        int i=wordService.deleteWord(word);
        return i == 0 ? R.error().message("删除词汇失败") : R.ok().message("删除词汇成功");

    }
    /**获取公共词汇库(已绑定固件的词汇)wz*/

    @Action
    public R getAllPublicWord (HttpServletRequest request) throws Exception {

        WordService wordService = IOC.getBean(WordService.class);
        List<Word> wordList = wordService.getAllWords();
        Map<String, Object> map = new HashMap<>();
        map.put("word", wordList);
        return R.ok().put("rows", map).message("返回公共词汇库成功");
    }


    /**/
    /*wz 根据词汇状态查询所有公共词汇getPublicWordsByStatus*/
    @Action
    public R getPublicWordsByStatus(HttpServletRequest request) throws Exception{
        WordService wordService = IOC.getBean(WordService.class);
        Integer status=RequestUtils.getIntegerParameter(request,"status");
        List<Word> wordList = wordService.getAllWordsByStatus(status);
        Map<String, Object> map = new HashMap<>();
        map.put("word", wordList);
        return R.ok().put("rows", map).message("根据词汇状态查询所有公共词汇，查询成功");
    }
    /*获取绑定某种固件种类的的所有公共词汇*/
    @Action
    public R  getPublicWordByUnitType (HttpServletRequest request) throws Exception{
        WordService wordService = IOC.getBean(WordService.class);
        Integer unitType=RequestUtils.getIntegerParameter(request,"unitType");
        List<Word> wordList = wordService.getPublicWordByUnitType(unitType);
        Map<String, Object> map = new HashMap<>();
        map.put("word", wordList);
        return R.ok().put("rows", map).message("根据固件种类查询所有公共词汇，查询成功");
    }
    /**wz查询审核通过的公共词汇getCheckedWord*/
    @Action
    public R getCheckedWord (HttpServletRequest request) throws Exception {

        WordService wordService = IOC.getBean(WordService.class);
        Integer status=RequestUtils.getIntegerParameter(request,"status");
        List<Word> wordList = wordService.getAllWordsByStatus(status);
        Map<String, Object> map = new HashMap<>();
        map.put("word", wordList);
        return R.ok().put("rows", map).message("返回审核通过的公共词汇库成功");
    }

    /**wz  根据创建者姓名查询公共词汇getWordByCreatorName*/
    @Action
    public R getWordByCreatorName(HttpServletRequest request) throws Exception{
        WordService wordService = IOC.getBean(WordService.class);
        String creatorName=RequestUtils.getStringParameter(request,"creatorName");
        List<Word> wordList=wordService.getAllWordsByCreatorName(creatorName);
        Map<String, Object> map = new HashMap<>();
        map.put("word", wordList);
        return R.ok().put("rows", map).message("根据创建者姓名查询公共词汇,获取成功");
    }
    /**wz 获取一个月内创建的词汇*/
    @Action
    public R  getAllWordsInMonth (HttpServletRequest request) throws Exception{
        WordService wordService = IOC.getBean(WordService.class);

        List<Word> wordList=wordService.getAllWordsInMonth();
        Map<String, Object> map = new HashMap<>();
        map.put("word", wordList);
        return R.ok().put("rows", map).message("获取近一个月内创建的词汇,获取成功");

    }

    /**wz 获取一周内创建的词汇*/
    @Action
    public R  getAllWordsInWeek (HttpServletRequest request) throws Exception{
        WordService wordService = IOC.getBean(WordService.class);

        List<Word> wordList=wordService.getAllWordsInWeek();
        Map<String, Object> map = new HashMap<>();
        map.put("word", wordList);
        return R.ok().put("rows", map).message("获取一周内创建的词汇,获取成功");

    }
    /**wz 获取一天内创建的词汇*/
    @Action
    public R  getAllWordsInDay (HttpServletRequest request) throws Exception{
        WordService wordService = IOC.getBean(WordService.class);

        List<Word> wordList=wordService.getAllWordsInDay();
        Map<String, Object> map = new HashMap<>();
        map.put("word", wordList);
        return R.ok().put("rows", map).message("获取今天内创建的词汇,获取成功");

    }
    ////////////////////////////////////////
    /*这里是获取未绑定固件的原始词汇*/
    /**获取还未绑定固件的词汇 ，可以在前台点击绑定固件按钮，将词汇绑定固件wz*/


    @Action
    public R getAllPublicWordNoUnit (HttpServletRequest request) throws Exception {

        WordService wordService = IOC.getBean(WordService.class);
        List<Word> wordList = wordService.getAllWordsNoUnit();
        Map<String, Object> map = new HashMap<>();
        map.put("word", wordList);
        return R.ok().put("rows", map).message("返回还未绑定固件的原始词汇成功");
    }
    @Action
    public R getAllWordsNoUnitByStatus (HttpServletRequest request) throws Exception {
        WordService wordService = IOC.getBean(WordService.class);
        Integer status=RequestUtils.getIntegerParameter(request,"status");
        List<Word> wordList = wordService.getAllWordsNoUnitByStatus(status);
        Map<String, Object> map = new HashMap<>();
        map.put("word", wordList);
        return R.ok().put("rows", map).message("根据词汇状态返回还未绑定固件的原始词汇成功");
    }
    @Action
    public R getAllWordsNoUnitByCreatorName (HttpServletRequest request) throws Exception {
        WordService wordService = IOC.getBean(WordService.class);
        String creatorName=RequestUtils.getStringParameter(request,"creatorName");
        List<Word> wordList = wordService.getAllWordsNoUnitByCreatorName(creatorName);
        Map<String, Object> map = new HashMap<>();
        map.put("word", wordList);
        return R.ok().put("rows", map).message("根据编辑者姓名获取还未绑定固件的原始词汇成功");
    }
    @Action
    public R  getAllWordsNoUnitInMonth (HttpServletRequest request) throws Exception{
        WordService wordService = IOC.getBean(WordService.class);

        List<Word> wordList=wordService.getAllWordsNoUnitInMonth();
        Map<String, Object> map = new HashMap<>();
        map.put("word", wordList);
        return R.ok().put("rows", map).message("获取一月内创建的还未绑定固件的原始词汇,获取成功");

    }
    @Action
    public R  getAllWordsNoUnitInWeek (HttpServletRequest request) throws Exception{
        WordService wordService = IOC.getBean(WordService.class);

        List<Word> wordList=wordService.getAllWordsNoUnitInWeek();
        Map<String, Object> map = new HashMap<>();
        map.put("word", wordList);
        return R.ok().put("rows", map).message("获取一周内创建的还未绑定固件的原始词汇,获取成功");

    }
    @Action
    public R  getAllWordsNoUnitInDay (HttpServletRequest request) throws Exception{
        WordService wordService = IOC.getBean(WordService.class);

        List<Word> wordList=wordService.getAllWordsNoUnitInDay();
        Map<String, Object> map = new HashMap<>();
        map.put("word", wordList);
        return R.ok().put("rows", map).message("获取一天内创建的还未绑定固件的原始词汇,获取成功");

    }
    /*wz 获取词汇的版本记录，就是每一个版本，list列出来，getAllWordVersionByWordId
    **/
    @Action
    public R getAllWordVersionByWordId(HttpServletRequest request) throws Exception{
        WordVersionService wordVersionService=IOC.getBean(WordVersionService.class);
        Long wordId=RequestUtils.getLongParameter(request,"wordId");
        List<WordVersion> wordVersionList=wordVersionService.getAllWordVersionByWordId(wordId);
        Map<String, Object> map = new HashMap<>();
        map.put("wordVersion", wordVersionList);
        return R.ok().put("rows", map).message("根据词汇id获取该词汇的所有版本,获取成功");
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    /*固件操作*/
    /*词汇绑定固件*/
    @Action
    public R addUnit (HttpServletRequest request) throws Exception {
        UnitService unitService=IOC.getBean(UnitService.class);
        JSONObject unitJsonObject = JSONObject.parseObject(RequestUtils.getStringParameter(request, "unit"));
        Unit unit= JsonUtils.getJavaObject(unitJsonObject, Unit.class);

       /* unit.setCreatorId(getUserID());
        unit.setCreatorName(getUserName());
        unit.setCreateTime(new Date());
        unit.setUpdateTime(new Date());*/
        unit.setWordId(unit.getWordId());
        unitService.insertUnit(unit);
        return R.ok().message("绑定固件成功");
    }
    @Action
    public R getAllUnitsByWordID(HttpServletRequest request) throws Exception{
        UnitService unitService = IOC.getBean(UnitService.class);
        String  wordId=RequestUtils.getStringParameter(request, "wordId");
        List<Unit> unitList = unitService.getAllUnitsByWordID(Long.valueOf(wordId));
        Map<String, Object> map = new HashMap<>();
        map.put("unit", unitList);
        return R.ok().put("rows", map).message("根据词汇id获取绑定的固件,获取成功");


    }
    //   //根据工具id获取该工具的所有部件,unit值工具的部件，专门为工具服务的，firmware是固件的统称，是大的
    @Action
    public R getUnitsByToolId(HttpServletRequest request) throws Exception{
        FirmwareService firmwareService = IOC.getBean(FirmwareService.class);
        String  toolId=RequestUtils.getStringParameter(request, "toolId");
        List<Firmware> unitList = firmwareService.getFirmwaresByToolId(Long.valueOf(toolId));
        Map<String, Object> map = new HashMap<>();
        map.put("unitList", unitList);
        return R.ok().put("rows", map).message("根据工具id获取该工具的所有部件,获取成功");

    }
    //根据固件id获取固件的相关信息
    @Action
    public R getUnitById (HttpServletRequest request) throws Exception{
        UnitService unitService = IOC.getBean(UnitService.class);
        String  unitId=RequestUtils.getStringParameter(request, "unitId");
        Unit unit=unitService.getUnitById(Long.valueOf(unitId));
        return R.ok().put("rows", unit).message("根据固件id获取固件的相关信息,获取成功");
    }
    //根据工具id和部件action获取固件的相关信息
    @Action
    public R getUnitByToolIdAndUnitAction (HttpServletRequest request) throws Exception{
        UnitService unitService = IOC.getBean(UnitService.class);
        String  toolId=RequestUtils.getStringParameter(request, "toolId");
        String  unitAction=RequestUtils.getStringParameter(request, "unitAction");
        Unit unit=unitService.getUnitByToolIdAndUnitAction(Long.valueOf(toolId),unitAction);
        return R.ok().put("rows", unit).message("根据工具id和部件action获取固件的相关信息,获取成功");
    }
    ///////////////////////////////////////////////////////////////////////////////////////
/**获取用户视图下的所有机构信息*/
    @Action
    public R getAllOrganization(HttpServletRequest request) throws Exception{

        IOrgToolRemoteService orgToolRemoteService = IOC.getBean(IOrgToolRemoteService.class);
        JSONObject organizationObject=orgToolRemoteService.getMyOrganList();
        JSONArray organizizationInfoList=organizationObject.getJSONArray("rows");

        return R.ok().put("rows", organizizationInfoList).put("total", organizationObject.getLong("total")).message("获取用户视图下的所有机构信息,获取成功");

    }
    /**根据机构id获取某个机构下的一级帮区信息getBandsByOrganizationId*/
    @Action
    public R getFirstBandsByOrganizationId(HttpServletRequest request) throws Exception {
        CoreRemoteService coreRemoteService= IOC.getBean(CoreRemoteService.class);
        Long organizationId=RequestUtils.getLongParameter(request,"organizationId");

        Map<String, Object> band=coreRemoteService.getBandByBandObjId(organizationId);
        Long bandViewId = Long.valueOf(band.get("objID").toString());
        JSONObject result = coreRemoteService.getBandsByBandObjId(bandViewId);

        return R.ok().put("rows", result.get("rows")).message("根据机构id获取某个机构下的一级帮区信息,获取成功");
    }


        // ----------------------------------------------------------------------------------------------------------------------

    /**
     * 查看业务类型（未分页，全部数据）
     */
    @Action
    public R getAllBusinessType(HttpServletRequest request) throws Exception {

        BusinessTypeService businessTypeService = IOC.getBean(BusinessTypeService.class);

        List<BusinessType> businessTypeList = businessTypeService.pageAllBusinessTypeList();
        Integer total = businessTypeService.pageAllBusinessTypeListTotal();
        return R.ok().put("rows", businessTypeList).put("total", total).message("获取业务类型成功");
    }

    /**
     * 查看业务类型
     */
    @Action
    public R getBusinessType(HttpServletRequest request) throws Exception {
        BusinessTypeService businessTypeService = IOC.getBean(BusinessTypeService.class);

        JSONObject businessType = JSONObject.parseObject(RequestUtils.getStringParameter(request, "businessType"));
        BusinessType businessTypeQuery = JsonUtils.getJavaObject(businessType, BusinessType.class);

        Integer pageSize = -1;
        Integer currentPage = -1;
        try {
            JSONObject pageInfo = JSONObject.parseObject(RequestUtils.getStringParameter(request, "pageInfo"));
            pageSize = (Integer)pageInfo.get("pageSize");
            currentPage = (Integer)pageInfo.get("currentPage");
        } catch (Exception e) {
        }

        List<BusinessType> businessTypeList =
            businessTypeService.pageConditionBusinessTypeList(businessTypeQuery, pageSize, currentPage);
        Integer total = businessTypeService.pageConditionBusinessTypeListTotal(businessTypeQuery);
        return R.ok().put("rows", businessTypeList).put("total", total).message("查询业务类型成功");
    }

    /**
     * 添加业务类型
     */
    @Action
    public R addBusinessType(HttpServletRequest request) throws Exception {
        BusinessTypeService businessTypeService = IOC.getBean(BusinessTypeService.class);

        JSONObject business = JSONObject.parseObject(RequestUtils.getStringParameter(request, "businessType"));
        BusinessType businessType = JsonUtils.getJavaObject(business, BusinessType.class);

        businessTypeService.insertBusinessType(businessType);

        return R.ok().message("添加业务类型成功");
    }

    /**
     * 修改业务类型
     */
    @Action
    public R updateBusinessType(HttpServletRequest request) throws Exception {
        BusinessTypeService businessTypeService = IOC.getBean(BusinessTypeService.class);

        JSONObject business = JSONObject.parseObject(RequestUtils.getStringParameter(request, "businessType"));
        BusinessType businessType = JsonUtils.getJavaObject(business, BusinessType.class);

        int i = businessTypeService.updateBusinessType(businessType);

        return i == 0 ? R.error().message("修改业务类型失败") : R.ok().message("修改业务类型成功");
    }

    /**
     * 删除业务类型
     */
    @Action
    public R deleteBusinessType(HttpServletRequest request) throws Exception {
        BusinessTypeService businessTypeService = IOC.getBean(BusinessTypeService.class);

        JSONObject business = JSONObject.parseObject(RequestUtils.getStringParameter(request, "businessType"));
        BusinessType businessType = JsonUtils.getJavaObject(business, BusinessType.class);

        int i = businessTypeService.deleteBusinessType(businessType);

        return i == 0 ? R.error().message("删除业务类型失败") : R.ok().message("删除业务类型成功");
    }

    // ----------------------------------------------------------------------------------------------------------------------
   /*获取某一业务类型下的所有案例*/
    @Action
    public R  getAppcaseByBusinessTypeId (HttpServletRequest request) throws Exception{
        ApplicationCaseService applicationCaseService = IOC.getBean(ApplicationCaseService.class);
        Long busineeTypeId = RequestUtils.getLongParameter(request, "businessTypeId");
        List<ApplicationCase> applicationCaseList=applicationCaseService.getAppcaseByBusinessId(busineeTypeId);
        return R.ok().put("rows", applicationCaseList).message("获取该业务类型下的所有案例，获取成功");

    }
    /**
     * 获取热点应用案例（按照访问量排序）
     */
    @Action
    public R getHotApplicationCase(HttpServletRequest request) throws Exception {
        ApplicationCaseService applicationCaseService = IOC.getBean(ApplicationCaseService.class);

        List<ApplicationCase> applicationCaseList = applicationCaseService.hotApplicationCaseList();

        return R.ok().put("rows", applicationCaseList).message("查询业务类型成功");
    }

    /**
     * 根据应用案例Id查询
     */
    @Action
    public R getApplicationCaseById(HttpServletRequest request) throws Exception {
        ApplicationCaseService applicationCaseService = IOC.getBean(ApplicationCaseService.class);

        Long applicationCaseId = RequestUtils.getLongParameter(request, "applicationCaseId");

        ApplicationCase applicationCase = applicationCaseService.getApplicationCaseById(applicationCaseId);
        return R.ok().put("rows", applicationCase).message("根据应用案例Id查询成功");
    }

    /**
     * 查看应用案例
     */
    @Action
    public R getApplicationCase(HttpServletRequest request) throws Exception {
        ApplicationCaseService applicationCaseService = IOC.getBean(ApplicationCaseService.class);

        JSONObject applicationCase =
            JSONObject.parseObject(RequestUtils.getStringParameter(request, "applicationCase"));
        ApplicationCase applicationCaseQuery = JsonUtils.getJavaObject(applicationCase, ApplicationCase.class);

        Integer pageSize = -1;
        Integer currentPage = -1;
        try {
            JSONObject pageInfo = JSONObject.parseObject(RequestUtils.getStringParameter(request, "pageInfo"));
            pageSize = (Integer)pageInfo.get("pageSize");
            currentPage = (Integer)pageInfo.get("currentPage");
        } catch (Exception e) {
        }

        List<ApplicationCase> applicationCaseList =
            applicationCaseService.pageConditionApplicationCaseList(applicationCaseQuery, pageSize, currentPage);
        Integer total = applicationCaseService.pageConditionApplicationCaseListTotal(applicationCaseQuery);
        return R.ok().put("rows", applicationCaseList).put("total", total).message("查询应用案例成功");
    }

    /**
     * 添加应用案例
     */
    @Action
    public R addApplicationCase(HttpServletRequest request) throws Exception {
        ApplicationCaseService applicationCaseService = IOC.getBean(ApplicationCaseService.class);

        JSONObject business = JSONObject.parseObject(RequestUtils.getStringParameter(request, "applicationCase"));
        ApplicationCase applicationCase = JsonUtils.getJavaObject(business, ApplicationCase.class);
        Date date = new Date();
        applicationCase.setCreateTime(date);
        applicationCase.setUpdateTime(date);
        applicationCaseService.insertApplicationCase(applicationCase);

        // 添加应用案例与相关帮区关联表
        ApplicationBandRelationService applicationBandRelationService =
            IOC.getBean(ApplicationBandRelationService.class);
        applicationBandRelationService.insertRelation(applicationCase.getBandList(), applicationCase.getId());

        return R.ok().message("添加应用案例成功");
    }

    /**
     * 修改应用案例
     */
    @Action
    public R updateApplicationCase(HttpServletRequest request) throws Exception {
        ApplicationCaseService applicationCaseService = IOC.getBean(ApplicationCaseService.class);

        JSONObject business = JSONObject.parseObject(RequestUtils.getStringParameter(request, "applicationCase"));
        ApplicationCase applicationCase = JsonUtils.getJavaObject(business, ApplicationCase.class);

        int i = applicationCaseService.updateApplicationCase(applicationCase);

        return i == 0 ? R.error().message("修改应用案例失败") : R.ok().message("修改应用案例成功");
    }

    /**
     * 删除应用案例
     */
    @Action
    public R deleteApplicationCase(HttpServletRequest request) throws Exception {
        ApplicationCaseService applicationCaseService = IOC.getBean(ApplicationCaseService.class);

        JSONObject business = JSONObject.parseObject(RequestUtils.getStringParameter(request, "applicationCase"));
        ApplicationCase applicationCase = JsonUtils.getJavaObject(business, ApplicationCase.class);

        int i = applicationCaseService.deleteApplicationCase(applicationCase);

        return i == 0 ? R.error().message("删除应用案例失败") : R.ok().message("删除应用案例成功");
    }
    //-----------------------------------------------------------------------------------------------------------------
    /**操作已发布的草稿剧本*/
    /**
     * wz
     * 发布草稿剧本到帮语网站
     */
    @Action
    public R shareToBlpWebSite(HttpServletRequest request) throws Exception {
        DraftReleaseService draftReleaseService = IOC.getBean(DraftReleaseService.class);
        JSONObject draftRelease = JSONObject.parseObject(RequestUtils.getStringParameter(request, "draftRelease"));
        if(draftRelease == null) {
            return R.error().message("请添加draftRelease参数");
        }
        DraftRelease draftReleaseParams = JsonUtils.getJavaObject(draftRelease, DraftRelease.class);
        draftReleaseParams.setCreateTime(new Date());
        Integer result = draftReleaseService.shareToBlpWebSite(draftReleaseParams);
        if(Objects.equals(result, DraftReleaseType.RELEASED_DRAFT.getValue())) {
            return R.ok().put("result", result).message("该版本的剧本已发布到帮语网站，不可重复发布");
        } else {
            return R.ok().put("result", result).message("发布到帮语网站成功");
        }
    }
    /**
     * wz
     * 根据draft id获取发布到帮语网站的草稿剧本*/
    @Action
    public R getReleaseDraftById (HttpServletRequest request) throws Exception{
        DraftReleaseService draftChapterService = IOC.getBean(DraftReleaseService.class);
        Long releaseDraftId = RequestUtils.getLongParameter(request, "releaseDraftId");
        DraftRelease draftRelease=draftChapterService.getReleaseDraftById(releaseDraftId);
        return R.ok().put("rows", draftRelease).message("根据Id查询到已发布剧本");
    }
    /**查看已发布到帮语网站的剧本wz*/
    @Action
    public R getDraftRelease(HttpServletRequest request) throws Exception{
        DraftReleaseService draftReleaseService=IOC.getBean(DraftReleaseService.class);

        JSONObject draftdraftRelease = JSONObject.parseObject(RequestUtils.getStringParameter(request, "draftRelease"));
        DraftRelease draftReleaseQuery = JsonUtils.getJavaObject(draftdraftRelease, DraftRelease.class);
        Integer pageSize = -1;
        Integer currentPage = -1;
        try {
            JSONObject pageInfo = JSONObject.parseObject(RequestUtils.getStringParameter(request, "pageInfo"));
            pageSize = (Integer)pageInfo.get("pageSize");
            currentPage = (Integer)pageInfo.get("currentPage");
        } catch (Exception e) {
        }

        List<DraftRelease> draftReleaseList = draftReleaseService.pageConditionDraftReleaseList(draftReleaseQuery, pageSize, currentPage);
        Integer total = draftReleaseService.pageConditionDraftReleaseListTotal(draftReleaseQuery);
        return R.ok().put("rows", draftReleaseList).put("total", total).message("查询已发布到帮语网站的剧本，查询成功");

    }


    // ----------------------------------------------------------------------------------------------------------------------

    /**
     * 查看草稿剧本
     */
    @Action
    public R getDraft(HttpServletRequest request) throws Exception {
        DraftService draftService = IOC.getBean(DraftService.class);

        JSONObject draft = JSONObject.parseObject(RequestUtils.getStringParameter(request, "draft"));
        Draft draftQuery = JsonUtils.getJavaObject(draft, Draft.class);

        Integer pageSize = -1;
        Integer currentPage = -1;
        try {
            JSONObject pageInfo = JSONObject.parseObject(RequestUtils.getStringParameter(request, "pageInfo"));
            pageSize = (Integer)pageInfo.get("pageSize");
            currentPage = (Integer)pageInfo.get("currentPage");
        } catch (Exception e) {
        }

        List<Draft> draftList = draftService.pageConditionDraftList(draftQuery, pageSize, currentPage);
        Integer total = draftService.pageConditionDraftListTotal(draftQuery);
        return R.ok().put("rows", draftList).put("total", total).message("查询草稿剧本成功");
    }
    @Action
    public R  getDraftListByAppcaseId(HttpServletRequest request) throws Exception{
        DraftService draftService = IOC.getBean(DraftService.class);
        String appcaseId=RequestUtils.getStringParameter(request, "appcaseId");
        List<Draft> draftList= draftService.getDraftListByAppcaseId(Long.valueOf(appcaseId));
        return R.ok().put("rows", draftList).message("根据应用案例获取剧本，获取成功");

    }
    //////////////////////////////////////////////////////////////////////////////////
/*发布一张帖子*/
    @Action
    public R  addPost(HttpServletRequest request) throws Exception{
        PostService postService=IOC.getBean(PostService.class);
        String title = RequestUtils.getStringParameter(request, "postTitle");

        String text = RequestUtils.getStringParameter(request, "postMainText");
        Post post= new Post();
        post.setCreatorName(getUserName());
        post.setCreateTime(new Date());
        post.setTitle(title);
        post.setContent(text);


        byte[] bs=getUploadFile("file").get();
        post.setImg(bs);
        postService.insertPost(post);
        return R.ok().message("发帖子成功");

    }

    @Action(resultHandler = BytesResultHandler.class)
    public byte[] getImage(HttpServletRequest request) throws Exception{
        PostService postService = IOC.getBean(PostService.class);

        Post post=postService.getPostById(RequestUtils.getLongParameter(request, "postId"));
        return post.getImg();
    }

/*获取所有帖子*/
        @Action
        public R getAllPosts(HttpServletRequest request) throws Exception{
            PostService postService = IOC.getBean(PostService.class);
            List<Post> postList = postService.getAllPost();
            Map<String, Object> map = new HashMap<>();
            map.put("post", postList);
            return R.ok().put("rows", map).message("返回帖子列表成功");
        }
        ////////////////////////////////////////////////////////////////////////////
    /*对帖子的评论部分*/
    /*获取对该帖子的评论列表*/
        @Action
        public R getCommentByPostId(HttpServletRequest request) throws Exception{
            CommentService commentService=IOC.getBean(CommentService.class);

            List<Comment> commentList=
                    commentService.getCommentByPostId(RequestUtils.getLongParameter(request, "postId"));

            Map<String, Object> map = new HashMap<>();
            map.put("commentList", commentList);
            return R.ok().put("rows", map).message("获取对该贴子的所有评论，获取成功");
        }
        /*发表评论*/
        @Action
        public R addComment(HttpServletRequest request) throws Exception {
            CommentService commentService = IOC.getBean(CommentService.class);
            String commentContent=RequestUtils.getStringParameter(request, "commentContent");
            Long postId=RequestUtils.getLongParameter(request, "postId");
            Comment comment=new Comment();
            comment.setContent(commentContent);
            comment.setCommentTime(new Date());
            comment.setCommentorName(getUserName());
            comment.setPostId(postId);
            commentService.insertComment(comment);
            return R.ok().put("rows",comment).message("对帖子评论成功");
        }
        /////////////////////////////////////////////////////////////
    /**
     * 帮语论坛个人信息部分，因为是从乌托邦的 用户来的，所以这个用户肯定是存在的，
    具备userAccount，用户名及账号是不可以在帮语网站改的
     我希望的是只改用户头像，所以在帮语网站只会出发updateUserTalkInfo方法*/
    @Action
    public R updateUserTalkInfo(HttpServletRequest request) throws Exception {
        UserTalkInfoService userTalkInfoService = IOC.getBean(UserTalkInfoService.class);
        byte[] bs=getUploadFile("file").get();
        UserTalkInfo userTalkInfo=new UserTalkInfo();
        userTalkInfo.setUserAvatar(bs);
        int i= userTalkInfoService.updateUserTalkInfo(userTalkInfo);
        return i == 0 ? R.error().message("修改用户头像失败") : R.ok().message("修改用户头像成功");
    }
    /**
     * 添加草稿剧本
     */
    @Action
    public R addDraft(HttpServletRequest request) throws Exception {
        DraftService draftService = IOC.getBean(DraftService.class);

        JSONObject business = JSONObject.parseObject(RequestUtils.getStringParameter(request, "draft"));
        Draft draft = JsonUtils.getJavaObject(business, Draft.class);
        // todo type字段先自己写，之后是由前端传来
        if (StringUtils.isEmpty(draft.getType())) {
            draft.setType(DraftTypeConstant.DRAFT_TYPE_NONSTANDARD);
       }
        Date date = new Date();
        draft.setCreateTime(date);
        draft.setUpdateTime(date);
        draftService.insertDraft(draft);

        return R.ok().message("添加草稿剧本成功");
    }

    /**
     * 修改草稿剧本
     */
    @Action
    public R updateDraft(HttpServletRequest request) throws Exception {
        DraftService draftService = IOC.getBean(DraftService.class);

        JSONObject business = JSONObject.parseObject(RequestUtils.getStringParameter(request, "draft"));
        Draft draft = JsonUtils.getJavaObject(business, Draft.class);

        int i = draftService.updateDraft(draft);

        return i == 0 ? R.error().message("修改草稿剧本失败") : R.ok().message("修改草稿剧本成功");
    }

    /**
     * 删除草稿剧本
     */
    @Action
    public R deleteDraft(HttpServletRequest request) throws Exception {
        DraftService draftService = IOC.getBean(DraftService.class);

        JSONObject business = JSONObject.parseObject(RequestUtils.getStringParameter(request, "draft"));
        Draft draft = JsonUtils.getJavaObject(business, Draft.class);

        int i = draftService.deleteDraft(draft);

        return i == 0 ? R.error().message("删除草稿剧本失败") : R.ok().message("删除草稿剧本成功");
    }

    // ----------------------------------------------------------------------------------------------------------------------
   /**g根据剧本id找对用的章节List*/
    @Action
    public R getDraftChapterListByDraftId(HttpServletRequest request) throws Exception {
        DraftChapterService draftChapterService = IOC.getBean(DraftChapterService.class);
        Long draftId = RequestUtils.getLongParameter(request, "draftId");
        List<DraftChapter> draftChapterList =draftChapterService.getDraftChapterListByDraftId(draftId);
        return R.ok().put("rows", draftChapterList).message("根据剧本Id找对应的章节List,获取成功");
    }
    /*获取所有的剧本，主要是为了获得草稿剧本，分析剧本，带上剧本这个空壳子 （只有名称和描述）步骤然后*/
    @Action
    public R getAllDraft(HttpServletRequest request) throws Exception {
        DraftService draftService = IOC.getBean(DraftService.class);
        AnalyseService analyseService = IOC.getBean(AnalyseService.class);
        DraftChapterService draftChapterService = IOC.getBean(DraftChapterService.class);
        Map<String,Object> map=null;
        List<Draft> draftList=draftService.getDraftList();
        List<DraftChapter> draftChapterList=null;
        List<Map<String,Object>> res=new ArrayList<>();


        for(Draft draft:draftList){
           // System.out.println("遍历剧本列表，当前剧本的id为"+draft.getId()+"剧本名称为"+draft.getName());
            draftChapterList=draftChapterService.getDraftChapterListByDraftId(draft.getId());
           // System.out.println("当前剧本的草稿语句条数是"+draftChapterList.size());

            List<Analyse> analyseList=new ArrayList<>();
            for(DraftChapter draftChapter:draftChapterList){
                //根据剧本章节id获取对应的分析剧本列表
             List<Analyse> analyseGroup=analyseService.getAnalyseByDraftChapterId(draftChapter.getId());
                //System.out.println("当前剧本的某一草稿语句对应的分析语句条数是"+analyseGroup.size());
                for(Analyse analyse:analyseGroup){
                    if(analyse!=null){
                        analyseList.add(analyse);
                    }

                }

            }
            System.out.println("当前剧本分析语句总条数是"+analyseList.size());
            map=new HashMap<>();
            map.put("draft",draft);
            map.put("draftChapterList",draftChapterList);
            map.put("analyseList",analyseList);

            res.add(map);

        }
        return R.ok().put("rows", res).message("获取所有的剧本，包括剧本本身空壳子和草稿剧本list和分析剧本list");
    }

    /*根据剧本id（剧本创建完成后只是个空壳子，只有name和description）获取草稿剧本（chapterList）和分析剧本（analyseList）*/
    @Action
    public R getDraftChapterListAndAnalyseListByDraftId(HttpServletRequest request) throws Exception {
        DraftChapterService draftChapterService = IOC.getBean(DraftChapterService.class);
        Long draftId = RequestUtils.getLongParameter(request, "draftId");
        List<DraftChapter> draftChapterList =draftChapterService.getDraftChapterListByDraftId(draftId);
        List<Analyse> analyseList=null;

        AnalyseService analyseService = IOC.getBean(AnalyseService.class);
        for(DraftChapter draftChapter:draftChapterList){
           List<Analyse> analyseGroup = analyseService.getAnalyseByDraftChapterId(draftChapter.getDraftId());
            for(Analyse analyse1:analyseGroup){
                if(analyse1!=null){
                    analyseList.add(analyse1);
                }
            }


        }
        Map<String,Object> map=new HashMap<>();
        map.put("draftChapterList",draftChapterList);
        map.put("analyseList",analyseList);
        return R.ok().put("rows", map).message("根据草稿剧本获取章节和分析剧本");
    }


    @Action
    public R getDraftchapterListOneToOneByAppcaseId(HttpServletRequest request) throws Exception {
        DraftChapterService draftChapterService = IOC.getBean(DraftChapterService.class);
        Long appcaseId = RequestUtils.getLongParameter(request, "appcaseId");
        List<DraftChapter> draftChapterList =draftChapterService.getDraftchapterListOneToOneByAppcaseId(appcaseId);
        return R.ok().put("rows", draftChapterList).message("根据应用案例Id找剧本及对应的章节List,获取成功");
    }
    /**
     * 根据 草稿剧本章节Id 查询
     */
    @Action
    public R getDraftChapterById(HttpServletRequest request) throws Exception {
        DraftChapterService draftChapterService = IOC.getBean(DraftChapterService.class);
        DraftService draftService = IOC.getBean(DraftService.class);
        Long draftChapterId = RequestUtils.getLongParameter(request, "draftChapterId");

        DraftChapter draftChapter = draftChapterService.getDraftChapterById(draftChapterId);
        Long draftId = draftChapter.getDraftId();

        Draft draft = draftService.getDraftById(draftId);
        draftChapter.setTitle(draft.getName());
        draftChapter.setDescription(draft.getDescription());
        draftChapter.setCreateTime(draft.getCreateTime());
        draftChapter.setUpdateTime(draft.getUpdateTime());
        draftChapter.setCreatorName(draft.getCreatorName());

        return R.ok().put("rows", draftChapter).message("根据草稿剧本章节Id查询");
    }

    /**
     * 根据草稿剧本Id查询 草稿剧本章节 和 对应的分析剧本
     */
    @Action
    public R getDraftChapterAndAnalyse(HttpServletRequest request) throws Exception {
        DraftChapterService draftChapterService = IOC.getBean(DraftChapterService.class);
        Long draftId = RequestUtils.getLongParameter(request, "draftId");

        DraftChapter draftChapter = draftChapterService.getDraftChapterAndAnalyse(draftId);

        return R.ok().put("draftChapter", draftChapter).message("根据草稿剧本Id查询 草稿剧本章节 和 对应的分析剧本 成功");
    }

    /**
     * 根据剧本Id查询 草稿剧本章节 和 对应的分析剧本 列表
     */
    @Action
    public R getDraftChapterAndAnalyseList(HttpServletRequest request) throws Exception {
        DraftChapterService draftChapterService = IOC.getBean(DraftChapterService.class);

        JSONObject draftChapter = JSONObject.parseObject(RequestUtils.getStringParameter(request, "draftChapter"));
        DraftChapter draftChapterQuery = JsonUtils.getJavaObject(draftChapter, DraftChapter.class);

        Integer pageSize = -1;
        Integer currentPage = -1;
        try {
            JSONObject pageInfo = JSONObject.parseObject(RequestUtils.getStringParameter(request, "pageInfo"));
            pageSize = (Integer)pageInfo.get("pageSize");
            currentPage = (Integer)pageInfo.get("currentPage");
        } catch (Exception e) {
        }

        List<DraftChapter> draftChapterList =
            draftChapterService.pageConditionDraftChapterList(draftChapterQuery, pageSize, currentPage);
        Integer total = draftChapterService.pageConditionDraftChapterListTotal(draftChapterQuery);
        return R.ok().put("rows", draftChapterList).put("total", total).message("根据草稿剧本Id查询 草稿剧本章节 和 对应的分析剧本 列表成功");
    }

    /**
     * 批量添加草稿剧本章节
     */
    @Action
    public R batchAddDraftChapter(HttpServletRequest request) throws Exception {
        DraftChapterService draftChapterService = IOC.getBean(DraftChapterService.class);
        JSONObject jsonObject = JSONObject.parseObject(RequestUtils.getStringParameter(request, "draftScript"));
        DraftScriptVo draftScriptVo = JsonUtils.getJavaObject(jsonObject, DraftScriptVo.class);

        draftChapterService.batchAddDraftChapter(draftScriptVo);

        return R.ok().message("批量添加草稿剧本章节成功");
    }

    /**
     * 添加草稿剧本章节
     */
    @Action
    public R addDraftChapter(HttpServletRequest request) throws Exception {
        DraftChapterService draftChapterService = IOC.getBean(DraftChapterService.class);

        JSONObject business = JSONObject.parseObject(RequestUtils.getStringParameter(request, "draftChapter"));
        DraftChapter draftChapter = JsonUtils.getJavaObject(business, DraftChapter.class);

        draftChapterService.insertDraftChapter(draftChapter);

        return R.ok().message("添加草稿剧本章节成功").put("draftId",draftChapter.getId());
    }

    /**
     * 修改草稿剧本章节
     */
    @Action
    public R updateDraftChapter(HttpServletRequest request) throws Exception {
        DraftChapterService draftChapterService = IOC.getBean(DraftChapterService.class);

        JSONObject business = JSONObject.parseObject(RequestUtils.getStringParameter(request, "draftChapter"));
        DraftChapter draftChapter = JsonUtils.getJavaObject(business, DraftChapter.class);

        int i = draftChapterService.updateDraftChapter(draftChapter);

        return i == 0 ? R.error().message("修改草稿剧本章节失败") : R.ok().message("修改草稿剧本章节成功");
    }

    /**
     * 删除草稿剧本章节
     */
    @Action
    public R deleteDraftChapter(HttpServletRequest request) throws Exception {
        DraftChapterService draftChapterService = IOC.getBean(DraftChapterService.class);

        JSONObject business = JSONObject.parseObject(RequestUtils.getStringParameter(request, "draftChapter"));
        DraftChapter draftChapter = JsonUtils.getJavaObject(business, DraftChapter.class);

        int i = draftChapterService.deleteDraftChapter(draftChapter);

        return i == 0 ? R.error().message("删除草稿剧本章节失败") : R.ok().message("删除草稿剧本章节成功");
    }

    // ----------------------------------------------------------------------------------------------------------------------

    /**
     * 根据已开发草稿剧本章节id查询对用分析剧本
     */
    @Action
    public R getAnalyseByDraftChapterId(HttpServletRequest request) throws Exception {
        AnalyseService analyseService = IOC.getBean(AnalyseService.class);

        Long draftChapterId = RequestUtils.getLongParameter(request, "draftChapterId");

        List<Analyse> analyse = analyseService.getAnalyseByDraftChapterId(draftChapterId);
        return R.ok().put("rows", analyse).message("根据已开发草稿剧本章节id查询对用分析剧本列表");
    }

    /**
     * 查看分析剧本
     */
    @Action
    public R getAnalyse(HttpServletRequest request) throws Exception {
        AnalyseService analyseService = IOC.getBean(AnalyseService.class);

        JSONObject analyse = JSONObject.parseObject(RequestUtils.getStringParameter(request, "analyse"));
        Analyse analyseQuery = JsonUtils.getJavaObject(analyse, Analyse.class);

        Integer pageSize = -1;
        Integer currentPage = -1;
        try {
            JSONObject pageInfo = JSONObject.parseObject(RequestUtils.getStringParameter(request, "pageInfo"));
            pageSize = (Integer)pageInfo.get("pageSize");
            currentPage = (Integer)pageInfo.get("currentPage");
        } catch (Exception e) {
        }

        List<Analyse> analyseList = analyseService.pageConditionAnalyseList(analyseQuery, pageSize, currentPage);
        Integer total = analyseService.pageConditionAnalyseListTotal(analyseQuery);
        return R.ok().put("rows", analyseList).put("total", total).message("查询分析剧本成功");
    }

    /**
     * 添加分析剧本
     */
    @Action
    public R addAnalyse(HttpServletRequest request) throws Exception {
        AnalyseService analyseService = IOC.getBean(AnalyseService.class);

        JSONObject business = JSONObject.parseObject(RequestUtils.getStringParameter(request, "analyse"));
        Analyse analyse = JsonUtils.getJavaObject(business, Analyse.class);

        analyseService.insertAnalyse(analyse);

        return R.ok().message("添加分析剧本成功");
    }

    /**
     * 修改分析剧本
     */
    @Action
    public R updateAnalyse(HttpServletRequest request) throws Exception {
        AnalyseService analyseService = IOC.getBean(AnalyseService.class);

        JSONObject business = JSONObject.parseObject(RequestUtils.getStringParameter(request, "analyse"));
        Analyse analyse = JsonUtils.getJavaObject(business, Analyse.class);

        int i = analyseService.updateAnalyse(analyse);

        return i == 0 ? R.error().message("修改分析剧本失败") : R.ok().message("修改分析剧本成功");
    }

    /**
     * 删除分析剧本
     */
    @Action
    public R deleteAnalyse(HttpServletRequest request) throws Exception {
        AnalyseService analyseService = IOC.getBean(AnalyseService.class);

        JSONObject business = JSONObject.parseObject(RequestUtils.getStringParameter(request, "analyse"));
        Analyse analyse = JsonUtils.getJavaObject(business, Analyse.class);

        int i = analyseService.deleteAnalyse(analyse);

        return i == 0 ? R.error().message("删除分析剧本失败") : R.ok().message("删除分析剧本成功");
    }

    // ----------------------------------------------------------------------------------------------------------------------
    /**
     * 查看操作日志
     */
    @Action
    public R getActionLog(HttpServletRequest request) throws Exception {
        ActionLogService actionLogService = IOC.getBean(ActionLogService.class);

        JSONObject actionLog = JSONObject.parseObject(RequestUtils.getStringParameter(request, "actionLog"));
        ActionLog actionLogQuery = JsonUtils.getJavaObject(actionLog, ActionLog.class);

        Integer pageSize = -1;
        Integer currentPage = -1;
        try {
            JSONObject pageInfo = JSONObject.parseObject(RequestUtils.getStringParameter(request, "pageInfo"));
            pageSize = (Integer)pageInfo.get("pageSize");
            currentPage = (Integer)pageInfo.get("currentPage");
        } catch (Exception e) {
        }

        List<ActionLog> actionLogList =
            actionLogService.pageConditionActionLogList(actionLogQuery, pageSize, currentPage);
        Integer total = actionLogService.pageConditionActionLogListTotal(actionLogQuery);
        return R.ok().put("rows", actionLogList).put("total", total).message("查询操作日志成功");
    }

    /**
     * 添加操作日志
     */
    @Action
    public R addActionLog(HttpServletRequest request) throws Exception {
        ActionLogService actionLogService = IOC.getBean(ActionLogService.class);

        JSONObject business = JSONObject.parseObject(RequestUtils.getStringParameter(request, "actionLog"));
        ActionLog actionLog = JsonUtils.getJavaObject(business, ActionLog.class);

        actionLogService.insertActionLog(actionLog);

        return R.ok().message("添加操作日志成功");
    }

    /**
     * 修改操作日志
     */
    @Action
    public R updateActionLog(HttpServletRequest request) throws Exception {
        ActionLogService actionLogService = IOC.getBean(ActionLogService.class);

        JSONObject business = JSONObject.parseObject(RequestUtils.getStringParameter(request, "actionLog"));
        ActionLog actionLog = JsonUtils.getJavaObject(business, ActionLog.class);

        int i = actionLogService.updateActionLog(actionLog);

        return i == 0 ? R.error().message("修改操作日志失败") : R.ok().message("修改操作日志成功");
    }

    /**
     * 删除操作日志
     */
    @Action
    public R deleteActionLog(HttpServletRequest request) throws Exception {
        ActionLogService actionLogService = IOC.getBean(ActionLogService.class);

        JSONObject business = JSONObject.parseObject(RequestUtils.getStringParameter(request, "actionLog"));
        ActionLog actionLog = JsonUtils.getJavaObject(business, ActionLog.class);

        int i = actionLogService.deleteActionLog(actionLog);

        return i == 0 ? R.error().message("删除操作日志失败") : R.ok().message("删除操作日志成功");
    }

    // ----------------------------------------------------------------------------------------------------------------------
    /**
     * 查看留言板
     */
    @Action
    public R getMessageBoardByApplicationCaseId(HttpServletRequest request) throws Exception {
        MessageBoardService messageBoardService = IOC.getBean(MessageBoardService.class);

        Long applicationCaseId = RequestUtils.getLongParameter(request, "applicationCaseId");

        List<MessageBoard> messageBoardList = messageBoardService.getMessageBoardByApplicationCaseId(applicationCaseId);
        return R.ok().put("rows", messageBoardList).message("查询留言板成功");
    }

    /**
     * 添加留言板
     */
    @Action
    public R addMessageBoard(HttpServletRequest request) throws Exception {
        MessageBoardService messageBoardService = IOC.getBean(MessageBoardService.class);

        JSONObject business = JSONObject.parseObject(RequestUtils.getStringParameter(request, "messageBoard"));
        MessageBoard messageBoard = JsonUtils.getJavaObject(business, MessageBoard.class);
        Date date = new Date();
        messageBoard.setTime(date);
        messageBoardService.insertMessageBoard(messageBoard);

        return R.ok().message("添加留言板成功");
    }

    /**
     * 删除留言板 (逻辑删除)
     */
    @Action
    public R deleteMessageBoard(HttpServletRequest request) throws Exception {
        MessageBoardService messageBoardService = IOC.getBean(MessageBoardService.class);

        JSONObject business = JSONObject.parseObject(RequestUtils.getStringParameter(request, "messageBoard"));
        MessageBoard messageBoard = JsonUtils.getJavaObject(business, MessageBoard.class);

        int i = messageBoardService.deleteMessageBoard(messageBoard);

        return i == 0 ? R.error().message("删除留言板失败") : R.ok().message("删除留言板成功");
    }
}
