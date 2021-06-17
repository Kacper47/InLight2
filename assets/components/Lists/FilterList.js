import React from 'react'
import styled from 'styled-components/native'
import { Text,
        FlatList, 
        StyleSheet,
        TouchableOpacity,
         View }
from 'react-native';

import FilterListItem from './FilterListItem'
import FilterListAutoItem from './FilterListAutoItem'

const FilterList = ({ title, filters, setFilter }) => {

    return(
        <ContainerView>
            <TitleText>{title}</TitleText>
            <FlatList
                data={filters}
                horizontal={true}
                renderItem={({item}) => 
                     item.isAuto 
                        ? (<FilterListAutoItem 
                            key={item.icon}
                            icon={item.icon}
                            size={item.size}
                            title={title}
                            label={item.name} 
                            setAutoFilter={setFilter}
                         /> )
                       : ( <FilterListItem 
                            key={item.icon}
                            icon={item.icon}
                            size={item.size}
                            title={title}
                            label={item.name} 
                            filterParams={item.filterParams}
                            setFilter={setFilter}
                          /> )
                }
            />

            {/* <FlatList
                data={filters}
                horizontal={true}
                renderItem={({item}) => 
                        <FilterListItem 
                            key={item.icon}
                            icon={item.icon}
                            size={item.size}
                            title={title}
                            label={item.name} 
                            filterParams={item.filterParams}
                            setFilter={setFilter}
                          />
                }
            />       */}
        </ContainerView>
    )
}

export default FilterList;

const ContainerView = styled(View)`
    width: 100%;
    height: 170px;
    margin-top: 40px;
`;

const TitleText = styled(Text)`
    font-size: 22px;
    font-weight: 400;
    font-family: Montserrat-SemiBold;
    margin: 5px;
    color: #FFF;
    margin-left: 15px;
`;





const V = styled(TouchableOpacity)`
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