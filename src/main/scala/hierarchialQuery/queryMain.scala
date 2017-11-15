/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main.scala.hierarchialQuery

import org.apache.spark.rdd.RDD
//import org.apache.spark.SparkContext
import org.apache.spark._
import org.apache.spark.graphx._

//import org.apache.spark.graphx.{Graph, VertexRDD}
import org.apache.spark.graphx.util.GraphGenerators
import scala.util.MurmurHash
import scala.collection.mutable.ListBuffer

//main entry 
object QueryMain {
  
  def main(args: Array[String]) {
    
    val appIdName = "Graph query with hierarhcial relation"
    val conf = new SparkConf().setAppName(appIdName)
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .set("spark.kryoserializer.buffer","24")               // Now it's 24 Mb of buffer by default instead of 0.064 Mb
      .set("spark.hadoop.validateOutputSpecs", "false")            // override output with the same path
    val sc = new SparkContext(conf)    //executeProductDatabase(args, sc)
    
    //executeProductDatabase(args, sc)
   // println("executeProductDatabase: done")
   // executeDblpGraphData(args, sc)
   // println("executeDblpGraphData: done")
   
   // testSyntheticGraph.executeSyntheticDatabase(args, sc)
   // println("executeSyntheticDatabase: done") 
    
    testSyntheticGraph.testTinyGraphData(args, sc)
    println("testSyntheticGraph: done")
    
    
  }
  
  //product database execution -- main entry
  def executeProductDatabase(args: Array[String], sc: SparkContext) = {
    
     // val file = "hdfs://localhost:8070/testEdgeListFile2")
   //val file = "hdfs://192.168.0.52:8070/testEdgeListFile2"
    //val inputfilePath = "/home/fubao/workDir/ResearchProjects/GraphQuerySearchRelatedPractice/Data/testInput/teshierarchicalAdjacencyList"
    
    //val inputAdjacencyListfilePath = "/home/fubao/workDir/ResearchProjects/hierarchicalNetworkQuery/inputData/ciscoProductVulnerability/newCiscoGraphAdjacencyList"
    //val inputNodeInfoFile = "/home/fubao/workDir/ResearchProjects/hierarchicalNetworkQuery/inputData/ciscoProductVulnerability/newCiscoGraphNodeInfo"
    
    //val outputFileNode = "/home/fubao/workDir/ResearchProjects/GraphQuerySearchRelatedPractice/SparkDistributedPractice/output/ciscoProduct/starQueryOutput/starQueryoutNode"
    //args [0] is TOPK number
    
    //val outputFilePath = "/home/fubao/workDir/ResearchProjects/GraphQuerySearchRelatedPractice/SparkDistributedPractice/output/ciscoProduct/starQueryOutput/starQueryoutPath"

    //test file
    //val inputAdjacencyListfilePath = "/home/fubao/workDir/ResearchProjects/GraphQuerySearchRelatedPractice/Data/testInput/test_smallGraphSpark"

    
    //read adjacency list to vertex edge RDD
    //val hierGraph = graphInputCommon.readAdjcencyListFile(sc, inputAdjacencyListfilePath)

    //graphInputCommon.test(sc)
        
    //starQuery.bfs(hierGraph, 1L, 15L)
    //starQuery.singleSourceGraphbfsTraverse(hierGraph, 1L, 1)
    //starQuery.starQueryGraphbfsTraverse(sc, hierGraph, List(1L, 5L), 1)
    
    //here is the main function entry for star query
    //experiment input list element (nodeId and type)
   
    //val dstTypeId = 0
    val topK = args(0).toInt
    val databaseType = 0
    val runTimeFileIndex = args(1)
    
    starQuery.TOPK = topK
   // val runTimeoutputFilePath = "/home/fubao/workDir/ResearchProjects/GraphQuerySearchRelatedPractice/SparkDistributedPractice/output/ciscoProduct/starQueryOutput/starQueryoutRuntime" + runTimeFileIndex

    /*
    val querySpecificNodeNumber = args(2).toInt
    val specificReadLst = inputQueryRead.getQuerySpecifiNodesLst(sc, inputNodeInfoFile, querySpecificNodeNumber)
    
    println("specificReadLst: ", specificReadLst.size)
    starQuery.starQueryExeute(sc, hierGraph, specificReadLst, dstTypeId, databaseType, inputNodeInfoFile, outputFileNode, outputFilePath, runTimeoutputFilePath)     //execute star query
    */
   
   /*
    val specificReadLst = List((2020L, 1), (9573L,5))
   //val specificReadLst = List((8987L, 4), (8330L,1))
    // val specificReadLst = List((5817L, 1), (5737L,1))
    starQuery.starQueryExeute(sc, hierGraph, specificReadLst, dstTypeId, databaseType, inputNodeInfoFile,  outputFilePath, runTimeoutputFilePath)     //execute star query
   */
  
    /*
    val runTimeFileIndex = args(1)
   //for varing top-k
    val runTimeoutputFilePath = "/home/fubao/workDir/ResearchProjects/GraphQuerySearchRelatedPractice/SparkDistributedPractice/output/ciscoProduct/nonStarQueryOutput/varingTopKOneMachine/nonstarQueryoutRuntime" + runTimeFileIndex

    //for non Star query
    //first get star query specific lists, two dimensional for two unknown nodes 6106
    //val specNodelistStarQueryTwoDimension: List[List[(VertexId, Int)]] = List(List((2020L, 1), (9573L,5)), List((5817L, 1), (5737L,1)))
    //val specNodelistStarQueryTwoDimension: List[List[(VertexId, Int)]] = List(List((9014L, 4), (7266L,1)), List((7266L, 1), (9573L,5)))
     val specNodelistStarQueryTwoDimension: List[List[(VertexId, Int)]] = List(List((8987L, 4), (8330L,1)), List((2020L, 1), (9573L,5)), List((5817L, 1), (5737L,1)))
     //val specNodelistStarQueryTwoDimension: List[List[(VertexId, Int)]] = List(List((8987L, 4), (8330L,1)), List((2020L, 1), (9573L,5)), List((5817L, 1), (5737L,1)), List((7266L, 1), (9573L,5)), List((9014L, 4), (7266L,1)))
     
    var dstTypeIdLstBuffer: ListBuffer[Int] = new ListBuffer[(Int)]
    for (specNodeLst <- specNodelistStarQueryTwoDimension)
    {
         
        dstTypeIdLstBuffer += (0)
    }
    print ("main dstTypeIdLstBuffer： " + dstTypeIdLstBuffer + "\n")
    val nonStarQueryTOPK = starQuery.TOPK
    nonStarQuery.nonStarQueryExecute(sc, hierGraph, specNodelistStarQueryTwoDimension, dstTypeIdLstBuffer, nonStarQueryTOPK, databaseType, inputNodeInfoFile, null, runTimeoutputFilePath)     //execute star query
    */
   
    
    /*
    // test varying query size
    val runTimeFileIndex = args(1)

    val inputFileSpecificStarQueryPath = "/home/fubao/workDir/ResearchProjects/hierarchicalNetworkQuery/hierarchicalQueryPython/output/extractSubgraphQueryOutput/ciscoDataExtractQueryGraph"
    
    val allquerySizeLsts = inputQueryRead.getQuerySizeNumber(sc, inputFileSpecificStarQueryPath)
   
    //print ("main allquerySizeLsts： " + allquerySizeLsts + "\n")
    //for varing query graph size
    var runTimeoutputFilePath = "/home/fubao/workDir/ResearchProjects/GraphQuerySearchRelatedPractice/SparkDistributedPractice/output/ciscoProduct/nonStarQueryOutput/varingSpecificSizeOneMachine/" + "queryGraphSize"
    
    var i = 0 
    var tmpRunTimeoutputFilePath = ""
    for (specNodelistStarQueryTwoDimension <- allquerySizeLsts)
    {
      
      tmpRunTimeoutputFilePath = runTimeoutputFilePath

      var dstTypeIdLstBuffer: ListBuffer[Int] = new ListBuffer[(Int)]
      for (specNodeLst <- specNodelistStarQueryTwoDimension)
      {

          dstTypeIdLstBuffer += (0)
      }
      print ("main dstTypeIdLstBuffer： " + dstTypeIdLstBuffer + "\n")
      val nonStarQueryTOPK = starQuery.TOPK
      i += 1
      tmpRunTimeoutputFilePath = tmpRunTimeoutputFilePath + i.toString + "_top" + nonStarQueryTOPK.toString + "_counts"  + runTimeFileIndex
      nonStarQuery.nonStarQueryExecute(sc, hierGraph, specNodelistStarQueryTwoDimension, dstTypeIdLstBuffer, nonStarQueryTOPK, databaseType, inputNodeInfoFile, null, tmpRunTimeoutputFilePath)     //execute non star query
      
    }
    */
   
    //test varing graphData in dblp data
   // val graphSizeRatio = args(2).toInt
    //val hierarchialRelation = true
  //  testVaringGraphDataProduct( sc, topK, runTimeFileIndex,  graphSizeRatio, databaseType, hierarchialRelation)
    
    //test w/o or w/ hierarchical relations
    val hierarchialRelation = false
    testHierarchicalRelationProductData (sc, topK, runTimeFileIndex, databaseType, hierarchialRelation)
    
  }
  
    
  //dblp data base execute --main entry
  def executeDblpGraphData(args: Array[String], sc: SparkContext) = {
      
    
    //val inputEdgeListfilePath = "../../Data/dblpParserGraph/output/finalOutput/newOutEdgeListFile.tsv"
    //val inputNodeInfoFilePath = "../../Data/dblpParserGraph/output/finalOutput/newOutNodeNameToIdFile.tsv"
        
    //read edge list to graphX graph
    //val hierGraph = graphInputCommon.readEdgeListFile(sc, inputEdgeListfilePath, inputNodeInfoFilePath, "\t")

    val dstTypeId = 1                     //1: people
    val topK = args(0).toInt
    starQuery.TOPK = topK
    
    val databaseType = 1              //DBLP database
    val runTimeFileIndex = args(1)

    //val specificReadLst = List((188470L, 3), (10821L,1))
    //val specificReadLst = List((189059L, 3), (10821L,1))
     //val specificReadLst = List((189015L, 3), (10821L,1))
    // val specificReadLst = List((188857L, 3))
    
    
    /*
    val outputFilePath = "../output/dblpData/starQueryOutput/starOutputFilePath" + runTimeFileIndex
    val runTimeoutputFilePath = "../output/dblpData/starQueryOutput/starQueryoutRuntime" + runTimeFileIndex
    starQuery.starQueryExeute(sc, hierGraph, specificReadLst, dstTypeId, databaseType, inputNodeInfoFilePath,  outputFilePath, runTimeoutputFilePath)     //execute star query
    */
  
    /*
    //start non-star query
    val runTimeoutputFilePath = "../output/dblpData/nonStarQueryOutput/nonStarQueryoutRuntime" + runTimeFileIndex
    val outputFilePath =  "../output/dblpData/nonStarQueryOutput/nonStarQueryOutputFilePath" + runTimeFileIndex
    val specNodelistStarQueryTwoDimension: List[List[(VertexId, Int)]] = List(List((189059L, 3)), List((189086L, 3)))
    //val specNodelistStarQueryTwoDimension: List[List[(VertexId, Int)]] = List(List((189059L, 3)), List((189086L, 3)),List((188857L, 3)))

    var dstTypeIdLstBuffer: ListBuffer[Int] = new ListBuffer[(Int)]
    for (specNodeLst <- specNodelistStarQueryTwoDimension)
    {
         
        dstTypeIdLstBuffer += (1)
    }
    print ("main dstTypeIdLstBuffer： " + dstTypeIdLstBuffer + "\n")
    val nonStarQueryTOPK = starQuery.TOPK
    nonStarQuery.nonStarQueryExecute(sc, hierGraph, specNodelistStarQueryTwoDimension, dstTypeIdLstBuffer, nonStarQueryTOPK, databaseType, inputNodeInfoFilePath, outputFilePath, runTimeoutputFilePath)     //execute star query
    
    */
    
    
    //begin topK K varing test
    /*
    val specificReadLst = List((189015L, 3), (10821L,1))
    val runTimeOutputFilePath = "../output/dblpData/starQueryOutput/varingTopKOneMachine/starQueryoutRuntime" + runTimeFileIndex
    starQuery.starQueryExeute(sc, hierGraph, specificReadLst, dstTypeId, databaseType, inputNodeInfoFilePath,  null, runTimeOutputFilePath)     //execute star query
    */
   
    //val specNodelistStarQueryTwoDimension: List[List[(VertexId, Int)]] = List(List((189059L, 3), (10821L,1)), List((189059L, 3), (189086L, 3)))
    //val specNodelistStarQueryTwoDimension: List[List[(VertexId, Int)]] = List(List((189059L, 3), (189086L, 3)))

    /* 
    val specNodelistStarQueryTwoDimension: List[List[(VertexId, Int)]] = List(List((189059L, 3), (10821L,1)), List((189059L, 3), (189086L, 3)), List((188857L, 3), (189086L, 3)))
    val runTimeOutputFilePath = "../output/dblpData/nonStarQueryOutput/varingTopKOneMachine/nonStarQueryoutRuntime" + runTimeFileIndex
    val outputFilePath = null      //"../output/dblpData/nonStarQueryOutput/varingTopKOneMachine/nonStarQueryOutputFilePath" + runTimeFileIndex

    var dstTypeIdLstBuffer: ListBuffer[Int] = new ListBuffer[(Int)]
    for (specNodeLst <- specNodelistStarQueryTwoDimension)
    {
         
        dstTypeIdLstBuffer += (1)
    }
    
    val nonStarQueryTOPK = starQuery.TOPK
    nonStarQuery.nonStarQueryExecute(sc, hierGraph, specNodelistStarQueryTwoDimension, dstTypeIdLstBuffer, nonStarQueryTOPK, databaseType, inputNodeInfoFilePath, outputFilePath, runTimeoutputFilePath)     //execute star query
    
    */
  
    /*
    //begin testing varying graph query size
    val inputFileSpecificStarQueryPath = "../../Data/extractSubgraph/output/extractDblpQuerySizeGraph/dblpDataExtractQueryGraph.tsv"
    
    val allquerySizeLsts = inputQueryRead.getQuerySizeNumber(sc, inputFileSpecificStarQueryPath)         //read query 
    val runTimeOutputFilePath = "../output/dblpData/nonStarQueryOutput/varyingQueryGraphSize_singleMachine/nonStarQueryOutRuntime" + runTimeFileIndex

    
    var i = 0 
    var tmpRunTimeOutputFilePath = ""
    //set destination noe type
    for (specNodelistStarQueryTwoDimension <- allquerySizeLsts)
    {
      
      tmpRunTimeOutputFilePath = runTimeOutputFilePath

      var dstTypeIdLstBuffer: ListBuffer[Int] = new ListBuffer[(Int)]
      for (specNodeLst <- specNodelistStarQueryTwoDimension)
      {

          dstTypeIdLstBuffer += (1)
      }
      
      print ("main  dblp dstTypeIdLstBuffer： " + dstTypeIdLstBuffer + "\n")
      val nonStarQueryTOPK = starQuery.TOPK
      i += 1
      tmpRunTimeOutputFilePath = tmpRunTimeOutputFilePath + i.toString + "_top" + nonStarQueryTOPK.toString + "_counts"  + runTimeFileIndex
      nonStarQuery.nonStarQueryExecute(sc, hierGraph, specNodelistStarQueryTwoDimension, dstTypeIdLstBuffer, 
                                   nonStarQueryTOPK, databaseType, inputNodeInfoFilePath, null, tmpRunTimeOutputFilePath)     //execute non star query
    
    }
   */
  
    //test varing graphData in dblp data
    //val graphSizeRatio = args(2).toInt
    //val hierarchialRelation = true
    //testVaringGraphDataDblp( sc, topK, runTimeFileIndex,  graphSizeRatio, databaseType, hierarchialRelation)
    
    val hierarchialRelation = false
    testHierarchicalRelationDblpData (sc, topK, runTimeFileIndex, databaseType, hierarchialRelation)
    
    
  }
  
  //function for testing varing graphData in dblp data
  def testVaringGraphDataDblp (sc: SparkContext, topK: Int, runTimeFileIndex: String, graphSizeRatio: Int, databaseType: Int, hierarchialRelation: Boolean) = {
    
    //test data graph size changing
    val varingGraphRatio = graphSizeRatio*0.1
    
    val inputNodeInfoFilePath = "../../Data/extractSubgraph/output/dblpDataGraphExtractOut/dataGraphInfo" +
                     varingGraphRatio.toString + "/nodeInfoPart" + varingGraphRatio.toString
    
    val inputEdgeListfilePath = "../../Data/extractSubgraph/output/dblpDataGraphExtractOut/dataGraphInfo" +
                     varingGraphRatio.toString + "/edgeListPart" + varingGraphRatio.toString
    
    val hierGraph = graphInputCommon.readEdgeListFile(sc, inputEdgeListfilePath, inputNodeInfoFilePath, "\t")
    
    print (" varingGraphRatio： " + varingGraphRatio + " " + inputEdgeListfilePath+ "\n")
    val runTimeOutputFilePath = "../output/dblpData/nonStarQueryOutput/varingDataGraphSizeOneMachine/nonStarQueryOutRuntime" + runTimeFileIndex

    val outputFilePath = null
    //val specNodelistStarQueryTwoDimension: List[List[(VertexId, Int)]] = List(List((189015L, 3), (10821L, 1)))
    //val specNodelistStarQueryTwoDimension: List[List[(VertexId, Int)]] = List(List((59897L, 3), (66520L, 2)))
    //val specNodelistStarQueryTwoDimension: List[List[(VertexId, Int)]] = List(List((59897L, 2), (66520L,2)), List((54314L, 2), (66488L, 2)))
    val specNodelistStarQueryTwoDimension: List[List[(VertexId, Int)]] = List(List((59897L, 2)), List((66520L,2), (123641L, 2)), List((59897L, 2)))
    
    var dstTypeIdLstBuffer: ListBuffer[Int] = new ListBuffer[(Int)]
    
    val nonStarQueryTOPK = topK
    for (specNodeLst <- specNodelistStarQueryTwoDimension)
    {
        dstTypeIdLstBuffer += (1)
    }
    
    
    print ("main dstTypeIdLstBuffer： " + dstTypeIdLstBuffer + "\n")
    nonStarQuery.nonStarQueryExecute(sc, hierGraph, specNodelistStarQueryTwoDimension, dstTypeIdLstBuffer, nonStarQueryTOPK, databaseType, inputNodeInfoFilePath, outputFilePath, runTimeOutputFilePath, hierarchialRelation)     //execute star query
    
  
  }
  
 
  //function for testing varing graphData in cisco product data
  def testVaringGraphDataProduct (sc: SparkContext, topK: Int, runTimeFileIndex: String, graphSizeRatio: Int, databaseType: Int, hierarchialRelation: Boolean) = {
    
    //test data graph size changing
    val varingGraphRatio = graphSizeRatio*0.1
    
    val inputNodeInfoFilePath = "../../../hierarchicalNetworkQuery/hierarchicalQueryPython/output/ciscoProductDataGraphExtractOut/dataGraphInfo" +
                     varingGraphRatio.toString + "/nodeInfoPart" + varingGraphRatio.toString
                     
    val inputEdgeListfilePath = "../../../hierarchicalNetworkQuery/hierarchicalQueryPython/output/ciscoProductDataGraphExtractOut/dataGraphInfo" +
                     varingGraphRatio.toString + "/edgeListPart" + varingGraphRatio.toString
    
    val hierGraph = graphInputCommon.readEdgeListFile(sc, inputEdgeListfilePath, inputNodeInfoFilePath, "\t")
    print (" varingGraphRatio： " + varingGraphRatio + " " + inputEdgeListfilePath+ "\n")
    val runTimeOutputFilePath = "../output/ciscoProduct/nonStarQueryOutput/varingDataGraphSizeOneMachine/nonStarQueryOutRuntime" + runTimeFileIndex
    val outputFilePath = null
    
    //val specNodelistStarQueryTwoDimension: List[List[(VertexId, Int)]] = List(List((9104L, 5), (6145L, 1)))
    //val specNodelistStarQueryTwoDimension: List[List[(VertexId, Int)]] = List(List((9104L, 5), (6145L, 1)), List((10210L, 6), (9923L, 6)))
    val specNodelistStarQueryTwoDimension: List[List[(VertexId, Int)]] = List(List((9104L, 5), (6145L, 1)), List((10210L, 6), (9923L, 6)), List((11300L, 6), (11095L, 6)))

    var dstTypeIdLstBuffer: ListBuffer[Int] = new ListBuffer[(Int)]
    
    val nonStarQueryTOPK = topK
    for (specNodeLst <- specNodelistStarQueryTwoDimension)
    {
        if (databaseType == 0){
            dstTypeIdLstBuffer += (0)                  //product type
        }
        else{
            dstTypeIdLstBuffer += (1)                  //people type here
        }
    }
    
    print ("main dstTypeIdLstBuffer： " + dstTypeIdLstBuffer + "\n")
    nonStarQuery.nonStarQueryExecute(sc, hierGraph, specNodelistStarQueryTwoDimension, dstTypeIdLstBuffer, nonStarQueryTOPK, databaseType, inputNodeInfoFilePath, outputFilePath, runTimeOutputFilePath, hierarchialRelation)     //execute star query
    
    
  }
   
  
  //test theresult w/o and w/ hierarchical relations in product data
  def testHierarchicalRelationProductData (sc: SparkContext, topK: Int, runTimeFileIndex: String, databaseType: Int, hierarchialRelation: Boolean) = {
   //based on star query check
   val inputEdgeListfilePath = "../../../hierarchicalNetworkQuery/hierarchicalQueryPython/output/ciscoProductDataGraphExtractOut/dataGraphInfo1.0/edgeListPart1.0"      //"../../../hierarchicalNetworkQuery/inputData/ciscoProductVulnerability/newCiscoGraphAdjacencyList"
   val inputNodeInfoFilePath = "../../../hierarchicalNetworkQuery/hierarchicalQueryPython/output/ciscoProductDataGraphExtractOut/dataGraphInfo1.0/nodeInfoPart1.0"
    
    /*
    var outputFilePath = ""
    if (hierarchialRelation){
        outputFilePath = "../output/ciscoProduct/starQueryOutput/testWithOrWORelations/testWithHierarchiOutput" + runTimeFileIndex + ".tsv"   
    }
    else{
        outputFilePath = "../output/ciscoProduct/starQueryOutput/testWithOrWORelations/testNoHierarchiOutput" + runTimeFileIndex + ".tsv"       
    }

    //read adjacency list to vertex edge RDD
    val runTimeoutputFilePath = null       //"../output/ciscoProduct/starQueryOutput/testWithOrWORelations/runTime" + runTimeFileIndex
    val hierGraph = graphInputCommon.readEdgeListFile(sc, inputEdgeListfilePath, inputNodeInfoFilePath, "\t")
    val specificReadLst = List((8330L, 1), (8987L,4))
    val dstTypeId = 0
    
    starQuery.starQueryExeute(sc, hierGraph, specificReadLst, dstTypeId, databaseType, inputNodeInfoFilePath,  outputFilePath, runTimeoutputFilePath, hierarchialRelation)     //execute star query
    
    */
   
    //test runtime with different query size
    val hierGraph = graphInputCommon.readEdgeListFile(sc, inputEdgeListfilePath, inputNodeInfoFilePath, "\t")
    val inputFileSpecificStarQueryPath = "../../../hierarchicalNetworkQuery/hierarchicalQueryPython/output/extractSubgraphQueryOutput/ciscoDataExtractQueryGraph"
    
    val allquerySizeLsts = inputQueryRead.getQuerySizeNumber(sc, inputFileSpecificStarQueryPath)
   
    //print ("main allquerySizeLsts： " + allquerySizeLsts + "\n")
    //for varing query graph size
    var runTimeoutputFilePath = ""
    if (hierarchialRelation){
        runTimeoutputFilePath = "../output/ciscoProduct/nonStarQueryOutput/testWithHierarchiOutputVaryingQuerySize/" + "queryGraphSize"
    }
    else{
        runTimeoutputFilePath = "../output/ciscoProduct/nonStarQueryOutput/testNoHierarchiOutputVaryingQuerySize/" + "queryGraphSize"
    }
    
    
    var i = 0 
    var tmpRunTimeoutputFilePath = ""
    for (specNodelistStarQueryTwoDimension <- allquerySizeLsts)
    {
      
      tmpRunTimeoutputFilePath = runTimeoutputFilePath

      var dstTypeIdLstBuffer: ListBuffer[Int] = new ListBuffer[(Int)]
      for (specNodeLst <- specNodelistStarQueryTwoDimension)
      {

          dstTypeIdLstBuffer += (0)
      }
      print ("main dstTypeIdLstBuffer： " + dstTypeIdLstBuffer + "\n")
      val nonStarQueryTOPK = starQuery.TOPK
      i += 1
      tmpRunTimeoutputFilePath = tmpRunTimeoutputFilePath + i.toString + "_top" + nonStarQueryTOPK.toString + "_counts"  + runTimeFileIndex
      nonStarQuery.nonStarQueryExecute(sc, hierGraph, specNodelistStarQueryTwoDimension, dstTypeIdLstBuffer, nonStarQueryTOPK, databaseType, inputNodeInfoFilePath, null, tmpRunTimeoutputFilePath, hierarchialRelation)     //execute non star query
      
    }
    
  }
  
  
  
  //test the result w/o and w/ hierarchical relations in dblp data
  def testHierarchicalRelationDblpData (sc: SparkContext, topK: Int, runTimeFileIndex: String, databaseType: Int, hierarchialRelation: Boolean) = {
   //based on star query check    
    val inputEdgeListfilePath = "../../Data/dblpParserGraph/output/finalOutput/newOutEdgeListFile.tsv"
    val inputNodeInfoFilePath = "../../Data/dblpParserGraph/output/finalOutput/newOutNodeNameToIdFile.tsv"

    val hierGraph = graphInputCommon.readEdgeListFile(sc, inputEdgeListfilePath, inputNodeInfoFilePath, "\t")

    /*
    var outputFilePath = ""
    if (hierarchialRelation){
        outputFilePath = "../output/dblpData/starQueryOutput/testWithOrWORelations/testWithHierarchiOutput" + runTimeFileIndex + ".tsv"   
    }
    else{
        outputFilePath = "../output/dblpData/starQueryOutput/testWithOrWORelations/testNoHierarchiOutput" + runTimeFileIndex + ".tsv"       
    }

    //read adjacency list to vertex edge RDD
    val runTimeoutputFilePath = null       //"../output/ciscoProduct/starQueryOutput/testWithOrWORelations/runTime" + runTimeFileIndex
    //val specificReadLst = List((188856L, 3), (9136L,1))
    val specificReadLst = List((189009L, 3), (9136L,1))
    
    val dstTypeId = 1
    
    starQuery.starQueryExeute(sc, hierGraph, specificReadLst, dstTypeId, databaseType, inputNodeInfoFilePath,  outputFilePath, runTimeoutputFilePath, hierarchialRelation)     //execute star query
    */
    
    val inputFileSpecificStarQueryPath = "../../Data/extractSubgraph/output/extractDblpQuerySizeGraph/dblpDataExtractQueryGraph.tsv"
    
    val allquerySizeLsts = inputQueryRead.getQuerySizeNumber(sc, inputFileSpecificStarQueryPath)         //read query 
    var runTimeoutputFilePath = ""        //"../output/dblpData/nonStarQueryOutput/varyingQueryGraphSize_singleMachine/nonStarQueryOutRuntime" + runTimeFileIndex

    if (hierarchialRelation){
        runTimeoutputFilePath = "../output/dblpData/nonStarQueryOutput/testWithHierarchiOutputVaryingQuerySize/" + "queryGraphSize"
    }
    else{
        runTimeoutputFilePath = "../output/dblpData/nonStarQueryOutput/testNoHierarchiOutputVaryingQuerySize/" + "queryGraphSize"
    }
    
     var i = 0 
    var tmpRunTimeoutputFilePath = ""
    for (specNodelistStarQueryTwoDimension <- allquerySizeLsts)
    {
       tmpRunTimeoutputFilePath = runTimeoutputFilePath

      var dstTypeIdLstBuffer: ListBuffer[Int] = new ListBuffer[(Int)]
      for (specNodeLst <- specNodelistStarQueryTwoDimension)
      {

          dstTypeIdLstBuffer += (0)
      }
      print ("main dstTypeIdLstBuffer： " + dstTypeIdLstBuffer + "\n")
      val nonStarQueryTOPK = starQuery.TOPK
      i += 1
      
    }
      
  
  }
   
    
}