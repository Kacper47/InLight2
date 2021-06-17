import React from 'react'
import styled from 'styled-components/native'
import { Image, Text, 
    TouchableOpacity, 
         View }
from 'react-native';

// import Icon from 'react-native-vector-icons/FontAwesome5';


const FilterListItem = ({ icon, size, title, label, filterParams, setFilter }) => {
    const iconImages = [
        require(`../../../assets/img/dim.png`),
        require(`../../../assets/img/filterA.png`),
        require(`../../../assets/img/filterB.png`),
        require(`../../../assets/img/sunrise.png`),
        require(`../../../assets/img/midday.png`),
        require(`../../../assets/img/sunset.png`),
        require(`../../../assets/img/campfire.png`),
        require(`../../../assets/img/AppIcon.png`),
    ];

    return(
        <ContainerView onPress={() => setFilter(icon, title, label, filterParams)}>
            <IconText>
                 <Image 
                     source={iconImages[icon]}
                     style={{width: size, height: size}}
                 />
            </IconText>
            <NameText>{label}</NameText>
        </ContainerView>
    )
}


export default FilterListItem;

const ContainerView = styled(TouchableOpacity)`
    width: 95px;
    height: 100px;
    margin: 5px 10px;
    border-radius: 15px;
    background: #404040;
    display: flex;
    align-items: center;
    justify-content: space-around;
`;

const NameText = styled(Text)`
    font-size: 13px;
    font-family: Montserrat-Regular;
    color: #FFF;
    margin: 8px;
    text-align: center;
    flex: 1;
`;

const IconText = styled(Text)`
    flex: 2;
`;