<?php

if(isset($_GET["keyword"])){

	       if($_GET['type']=='Users'){
            header('Content-Type: application/json');
            $url="https://graph.facebook.com/v2.8/search?q=".$_GET["keyword"]."&type=user&fields=id,name,picture.width(700).height(700)&access_token=EAAWAX7KyU2oBAI3ywc89ZCFfUQ2mRugfVGs6caZBLlGFyzVWB6oKECnZBZAUjKuYAxYb8Qx0aw5i7PYK4Y4ZBgigZBmN9MfEVZAba22GVu57LC6XPi8AyCE2gICLk25fMfXGmhtVZCY6Hm6EMGN0Hc0A8cXADycRTbQZD";
            $url=preg_replace( '/ /', '%20', $url);
            $fresponse = file_get_contents($url,false);
            //$result = json_decode($fresponse, true);
            echo $fresponse;
           }



           if($_GET["type"]=='Pages'){
            header('Content-Type: application/json');
            $url="https://graph.facebook.com/v2.8/search?q=".$_GET["keyword"]."&type=page&fields=id,name,picture.width(700).height(700)&access_token=EAAWAX7KyU2oBAI3ywc89ZCFfUQ2mRugfVGs6caZBLlGFyzVWB6oKECnZBZAUjKuYAxYb8Qx0aw5i7PYK4Y4ZBgigZBmN9MfEVZAba22GVu57LC6XPi8AyCE2gICLk25fMfXGmhtVZCY6Hm6EMGN0Hc0A8cXADycRTbQZD";
            $url=preg_replace( '/ /', '%20', $url);
            $fresponse = file_get_contents($url,false);
            //$result = json_decode($fresponse, true);
            echo $fresponse;
           }



           if($_GET["type"]=='Events'){
            header('Content-Type: application/json');
            $url="https://graph.facebook.com/v2.8/search?q=".$_GET["keyword"]."&type=event&fields=id,name,picture.width(700).height(700)&access_token=EAAWAX7KyU2oBAI3ywc89ZCFfUQ2mRugfVGs6caZBLlGFyzVWB6oKECnZBZAUjKuYAxYb8Qx0aw5i7PYK4Y4ZBgigZBmN9MfEVZAba22GVu57LC6XPi8AyCE2gICLk25fMfXGmhtVZCY6Hm6EMGN0Hc0A8cXADycRTbQZD";
            $url=preg_replace( '/ /', '%20', $url);
            $fresponse = file_get_contents($url,false);
            //$result = json_decode($fresponse, true);
            echo $fresponse;
           }

		   if($_GET["type"]=='Places'){
            header('Content-Type: application/json');
           $url="https://graph.facebook.com/v2.8/search?q=".$_GET['keyword']."&type=place&center=".$_GET['lat'].",".$_GET['long']."&fields=id,name,picture.width(700).height(700)&access_token=EAAWAX7KyU2oBAI3ywc89ZCFfUQ2mRugfVGs6caZBLlGFyzVWB6oKECnZBZAUjKuYAxYb8Qx0aw5i7PYK4Y4ZBgigZBmN9MfEVZAba22GVu57LC6XPi8AyCE2gICLk25fMfXGmhtVZCY6Hm6EMGN0Hc0A8cXADycRTbQZD"; 
           
            $url=preg_replace( '/ /', '%20', $url);
            $fresponse = file_get_contents($url,false);
            //$result = json_decode($fresponse, true);
            echo $fresponse;
           }


           


          if($_GET["type"]=='Groups'){
            header('Content-Type: application/json');
            $url="https://graph.facebook.com/v2.8/search?q=".$_GET["keyword"]."&type=group&fields=id,name,picture.width(700).height(700)&access_token=EAAWAX7KyU2oBAI3ywc89ZCFfUQ2mRugfVGs6caZBLlGFyzVWB6oKECnZBZAUjKuYAxYb8Qx0aw5i7PYK4Y4ZBgigZBmN9MfEVZAba22GVu57LC6XPi8AyCE2gICLk25fMfXGmhtVZCY6Hm6EMGN0Hc0A8cXADycRTbQZD";
            $url=preg_replace( '/ /', '%20', $url);
            $fresponse = file_get_contents($url,false);
            echo $fresponse;
           }
        }


         if(isset($_GET["uid"])){
        header('Content-Type: application/json');
        $url="https://graph.facebook.com/v2.8/".$_GET["uid"]."? fields= id,name,picture,albums.limit(5){name,photos.limit(2){name,
            picture}},posts.limit(5){message,story,created_time}&access_token=EAAWAX7KyU2oBAI3ywc89ZCFfUQ2mRugfVGs6caZBLlGFyzVWB6oKECnZBZAUjKuYAxYb8Qx0aw5i7PYK4Y4ZBgigZBmN9MfEVZAba22GVu57LC6XPi8AyCE2gICLk25fMfXGmhtVZCY6Hm6EMGN0Hc0A8cXADycRTbQZD";
        $url=preg_replace( '~\s~', '%20', $url);
        $dresponse = file_get_contents($url,false);
        echo $dresponse;

    }
    if(isset($_GET["eid"])){
        header('Content-Type: application/json');
        $url="https://graph.facebook.com/v2.8/".$_GET["eid"]."? fields= id,name,picture&access_token=EAAWAX7KyU2oBAI3ywc89ZCFfUQ2mRugfVGs6caZBLlGFyzVWB6oKECnZBZAUjKuYAxYb8Qx0aw5i7PYK4Y4ZBgigZBmN9MfEVZAba22GVu57LC6XPi8AyCE2gICLk25fMfXGmhtVZCY6Hm6EMGN0Hc0A8cXADycRTbQZD";
        $url=preg_replace( '~\s~', '%20', $url);
        $dresponse = file_get_contents($url,false);
        echo $dresponse;

    }
	
	
	  

           if(isset($_GET['param1']))
          {
          	header('content-Type:application/json');
          	$url=$_GET['param1'];
            $url=preg_replace( '~\s~', '%20', $url);
               $dresponse = file_get_contents($url,false);
               echo $dresponse;
          }

if(isset($_GET['fbid'])) {
   header('Content-Type: application/json');
        $url="https://graph.facebook.com/v2.8/".$_GET["fbid"]."? fields= id,name,picture&access_token=EAAWAX7KyU2oBAI3ywc89ZCFfUQ2mRugfVGs6caZBLlGFyzVWB6oKECnZBZAUjKuYAxYb8Qx0aw5i7PYK4Y4ZBgigZBmN9MfEVZAba22GVu57LC6XPi8AyCE2gICLk25fMfXGmhtVZCY6Hm6EMGN0Hc0A8cXADycRTbQZD";
        $url=preg_replace( '~\s~', '%20', $url);
        $dresponse = file_get_contents($url,false);
        echo $dresponse;
}

if(isset($_GET["postid"])){
        header('Content-Type: application/json');
        $url="https://graph.facebook.com/".$_GET["postid"]."?fields=picture.width(500).height(500)&access_token=EAAWAX7KyU2oBAI3ywc89ZCFfUQ2mRugfVGs6caZBLlGFyzVWB6oKECnZBZAUjKuYAxYb8Qx0aw5i7PYK4Y4ZBgigZBmN9MfEVZAba22GVu57LC6XPi8AyCE2gICLk25fMfXGmhtVZCY6Hm6EMGN0Hc0A8cXADycRTbQZD";
        $url=preg_replace( '~\s~', '%20', $url);
        $postresponse = file_get_contents($url,false);
        echo $postresponse;

    }
if(isset($_GET["favid"])){
            header('Content-Type: application/json');
            $url="https://graph.facebook.com/v2.8/".$_GET["favid"]."?fields=id,name,picture&access_token=EAAWAX7KyU2oBAI3ywc89ZCFfUQ2mRugfVGs6caZBLlGFyzVWB6oKECnZBZAUjKuYAxYb8Qx0aw5i7PYK4Y4ZBgigZBmN9MfEVZAba22GVu57LC6XPi8AyCE2gICLk25fMfXGmhtVZCY6Hm6EMGN0Hc0A8cXADycRTbQZD";
            $url=preg_replace( '/ /', '%20', $url);
            $fresponse = file_get_contents($url,false);
            //$result = json_decode($fresponse, true);
            echo $fresponse;
           }




?>