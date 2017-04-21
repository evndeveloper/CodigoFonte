<?php

if($_POST["pesq"]){
	$variavel = $_POST["pesq"];
	$con = mysqli_connect("localhost","root","","theone") or die("Couldn't connect");
	$result = mysqli_query($con, "SELECT * FROM theone.num_contatos where a='". $variavel ."'");

	$nodes= array();

	$x = 0;
	$y = 0;
	$noA = true;

	while ($row = mysqli_fetch_assoc($result)) {
		$x = $x + 10;
		$y = $y + 10;	

		if($noA){
			$infonode = array('data' => array(
	        'id' =>  str_replace("p", "", $row['a']),
	        'idInt' => str_replace("p", "", $row['a']),
	        'name' => str_replace("p", "", $row['a']),
	        'score' => $row['cont'],
	        'query' => true,
	    	'gene' => true    	       
		    ),
			'position' => array(
		        'x' => $x,
		        'y' => $y
		    ),
			'group' => "nodes",
		    'removed' => false,
		    'selected' => false,
		    'selectable' => true,
		    'locked' => false,
		    'grabbed' => false,
		    'grabbable' => true
		    );   
		    array_push($nodes, $infonode);
		    $x = $x + 10;
			$y = $y + 10;
			$noA = false;

		}

		$infonode = array('data' => array(
	        'id' =>  str_replace("p", "", $row['b']),
	        'idInt' => str_replace("p", "", $row['b']),
	        'name' => str_replace("p", "", $row['b']),
	        'score' => 10,
	        'query' => true,
	    	'gene' => true    	       
	    ),
		'position' => array(
	        'x' => $x,
	        'y' => $y
	    ),
		'group' => "nodes",
	    'removed' => false,
	    'selected' => false,
	    'selectable' => true,
	    'locked' => false,
	    'grabbed' => false,
	    'grabbable' => true
	    );   
	    array_push($nodes, $infonode);

	    $edge = array('data' => array(
	    		'source' => str_replace("p", "", $row['a']),
			    'target' => str_replace("p", "", $row['b']),
			    'weight' => $row['cont'],
			   	),
	    		'group' => "edges",
			  	'removed' => false,
			  	'selected' => false,
			  	'selectable' => true,
			  	'locked' => false,
			  	'grabbed' => false,
			  	'grabbable' => true,
			  	'classes' => ""

	    );
	    	
	    array_push($nodes, $edge);       
	}
	    $jsonstring = json_encode($nodes);
		echo $jsonstring;  

}


?>