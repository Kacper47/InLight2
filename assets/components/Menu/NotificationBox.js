//React fundaments
import React from 'react'
import styled from 'styled-components/native'
import { Text,
        TouchableOpacity, 
        Image,
         View }
from 'react-native';
//Slider
import Slider from '@react-native-community/slider';
//Icons
// import Icon from 'react-native-vector-icons/FontAwesome5';

const NotificationBox = ({isFilterActive, 
                          myFilterIcon, myFilterTitle, myFilterLabel, 
                          mySliderValue, onChangeFilter,
                          stopFilter}) => {

    return(
        <NotificationView isFilterActive={isFilterActive}>
          <FlexView>
             <RowNotificationView>
                <CategoryText>
                  <Image source={iconImages[myFilterIcon]} style={{width: 20, height: 20}}/>
                      {` ${myFilterTitle}`}
                </CategoryText>
                <FilterText>{myFilterLabel}</FilterText>
             </RowNotificationView>

             <RowNotificationView>
               <RemoveButton onPress={stopFilter}>
                  <Image source={iconImages[8]} style={{width: 40, height: 40}}/>
               </RemoveButton>
             </RowNotificationView>   
          </FlexView>

          <IntensityView>
            <IntensityText>Natężenie: </IntensityText>
            <IntensityValueText>{`${Math.floor(mySliderValue*100)}%`}</IntensityValueText>
          </IntensityView>
           <Slider
             style={{width: '86%', height: 40, marginLeft: '7%'}} minimumValue={0} maximumValue={1} minimumTrackTintColor="#ffa700" maximumTrackTintColor="#FFFFFF" thumbTintColor="#ffa700" value={mySliderValue} onValueChange={(val) => onChangeFilter(val)}
           />
        </NotificationView>
    )
}


const NotificationView = styled(View)`
  background: #d4d4d4;
  width: 100%;
  height: 28%;
  opacity: 0.8;
  border-radius: 15px;
  margin-top: 10%;
`;

const FlexView = styled(View)`
  display: flex;
  flex-direction: row;
`;

const RowNotificationView = styled(View)`
  width: 50%;
  height: 100%;
`;


const CategoryText = styled(Text)`
  color: #ffa700;
  font-size: 13px;
  font-family: Montserrat-Regular;
  margin: 10px 0px 0px 20px;
`;
const FilterText = styled(Text)`
  color: #000;
  font-size: 22px;
  font-family: Montserrat-Regular;
  margin: 0px 0px 0px 20px;
`;
const RemoveButton = styled(TouchableOpacity)`
  width: 40px;
  height: 40px;
  display: flex;
  margin: 15% 60%;
`;

const IntensityView = styled(View)`
  display: flex;
  flex-direction: row;
`;
const IntensityText = styled(Text)`
  font-size: 15px;
  margin: 20px 0px 0px 20px;
`;
const IntensityValueText = styled(Text)`
  font-size: 30px;
  margin: 5px 0px 0px 5px;
`;

export default NotificationBox;