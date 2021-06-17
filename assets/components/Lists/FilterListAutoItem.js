import React from 'react'
import styled from 'styled-components/native'
import { Image, Text, 
    TouchableOpacity, 
         View }
from 'react-native';

// import Icon from 'react-native-vector-icons/FontAwesome5';


const FilterListAutoItem = ({ icon, size, title, label, setAutoFilter }) => {
    const iconImages = [
        require(`../../../assets/img/AppIcon.png`)
    ];

    return(
        <ContainerView onPress={() => setAutoFilter(icon, title, label)}>
            <IconText>
                 <Image 
                     source={iconImages[0]}
                     style={{width: size, height: size}}
                 />
            </IconText>
            <NameText>{label}</NameText>
        </ContainerView>
    )
}


export default FilterListAutoItem;

const ContainerView = styled(TouchableOpacity)`
    width: 100px;
    height: 100px;
    margin: 5px 10px;
    border-radius: 50px;
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