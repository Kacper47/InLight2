/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow strict-local
 */

//React fundamentals
import React, {useState, useEffect} from 'react';
import {useMonth} from './assets/hooks/useMonth'
import {
  ScrollView,
  Text,
  ImageBackground,
  View,
  NativeModules, 
  Linking
} from 'react-native';
//Styled
import styled from 'styled-components/native';
//Components
import NotificationBox from './assets/components/Menu/NotificationBox'
import FilterList from './assets/components/Lists/FilterList';
//Datas
import {ClassicFilters, SunCyclesFilters, AutoFilters} from './assets/datas/Filters';
//Splash screen
import SplashScreen from 'react-native-splash-screen';
//Location
import Geolocation from 'react-native-geolocation-service';
//Permissions
import RNDrawOverlay from 'react-native-draw-overlay';
//Async storage
import AsyncStorage from '@react-native-async-storage/async-storage';



const App = () => {
  // const setAsyncValue = () => {
  //   AsyncStorage.setItem('DES', true)    
  // }

  // //Wyjmowanie z bazy zmiennej o kluczu 'DES' przy useEffect
  // useEffect(() => {
  //   AsyncStorage.getItem('DES').then(val => {
  //   console.log(val);
  // })
  // }, [])

  //Splash screen
  useEffect(() => {
    SplashScreen.hide();
  })


  //TIME AND DATE
  let date = new Date();

  const year = date.getFullYear();
  const month = useMonth(date.getMonth());
  const day = date.getDate();
  let hours = date.getHours();
  let minutes = date.getMinutes();
  let seconds = date.getSeconds();


  //FILTER
  let { Filter } = NativeModules; 

  const [mySliderValue, setSliderValue] = useState(0.5);
  const [myRed, setRed] = useState(0);
  const [myGreen, setGreen] = useState(0);
  const [myBlue, setBlue] = useState(0);

  const [isFilterActive, setFilterActive] = useState(false);
  const [myFilterIcon, setFilterIcon] = useState('');
  const [myFilterTitle, setFilterTitle] = useState('');
  const [myFilterLabel, setFilterLabel] = useState('');
  
  const setFilter = (icon, title, label, filterParams) => {
    RNDrawOverlay.askForDispalayOverOtherAppsPermission()
       .then(res => {
          setFilterActive(true)
          setFilterIcon(icon)
          setFilterTitle(title)
          setFilterLabel(label)
        
          setSliderValue(filterParams.alpha/200)
          setRed(filterParams.red);
          setGreen(filterParams.green);
          setBlue(filterParams.blue);

          Filter.startFilterService(label, title, false, 
                                    filterParams.alpha, filterParams.red, 
                                    filterParams.green, filterParams.blue);
       })
       .catch(e => {
          Linking.openSettings();
       })
  }




   var SunCalc = require('suncalc');

   const setAutoFilter = (icon, title, label) => {
     //Permission
    RNDrawOverlay.askForDispalayOverOtherAppsPermission()
       .then(res => {
          Geolocation.getCurrentPosition(
            (position) => {
             //Sun times
             let times = SunCalc.getTimes(new Date(), position.coords.latitude, position.coords.longitude);
             let timeInMinutes = hours*60 + minutes;
             let sunriseInMinutes = times.sunrise.getHours()*60 + times.sunrise.getMinutes();
             let sunsetInMinutes = times.sunset.getHours()*60 + times.sunset.getMinutes();
            
             //Activating service
             setFilterActive(true)
             setFilterIcon(icon)
             setFilterTitle(title)
             setFilterLabel(label)
             Filter.startAutoService(label, title, true, timeInMinutes, sunriseInMinutes, sunsetInMinutes);
            },
            (error) => {
              console.log(error.code, error.message);
            },
            { enableHighAccuracy: true, timeout: 5000, maximumAge: 10000 }
          );
       })
       .catch(e => {
          Linking.openSettings();
       })
     
   }


  const onChangeFilter = (val) => {
    setSliderValue(val);
    Filter.startFilterService(myFilterLabel, myFilterTitle, false, val * 200, myRed, myGreen, myBlue)
   }

   const stopFilter = () => {
    Filter.stopService(); 
    setFilterActive(false);
  }

  return (
      <ContainerView> 
        <MenuImage source={require('./assets/img/MenuLandscape.jpg')}>
          <MarginView>
            <WelcomeText>Witaj</WelcomeText>
            <DateText>{`${day} ${month} ${year}`}</DateText>
            {isFilterActive
              ?  <NotificationBox 
                    isFilterActive={isFilterActive} 
                    myFilterTitle={myFilterTitle}
                    myFilterIcon={myFilterIcon}
                    myFilterLabel={myFilterLabel}
                    mySliderValue={mySliderValue}
                    onChangeFilter={onChangeFilter} 
                    stopFilter={stopFilter}
                  />
              :  <InfoText>Tutaj będą twoje wybrane filtry</InfoText>
            }
          </MarginView>
        </MenuImage>
        <FilterList title={'Filtry'} filters={ClassicFilters} setFilter={setFilter}/>
        <FilterList title={'Cykle słoneczne'} filters={SunCyclesFilters} setFilter={setFilter}/>
        <FilterList title={'Automatyczne filtry'} filters={AutoFilters} setFilter={setAutoFilter}/>
      </ContainerView>
  );
};



const ContainerView = styled(ScrollView)`
  width: 100%;
  height: 100%;
  background: #202020;
`;
const WelcomeText = styled(Text)`
  color: #FFF;
  font-size: 35px;
  font-family: Montserrat-SemiBold;
  margin-top: 30%;
`;

const MenuImage = styled(ImageBackground)`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 700px;
  margin-bottom: 40px;
`;

const MarginView = styled(View)`
  width: 80%;
  height: 100%;
`;


const DateText = styled(Text)`
  color: #FFF;
  font-size: 20px;
  font-family: Montserrat-SemiBold;
 
`;
const InfoText = styled(Text)`
  color: #FFF;
  font-size: 20px;
  text-align: center;
  margin-top: 45px;
`;


export default App;
