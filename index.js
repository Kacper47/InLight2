/**
 * @format
 */

import {AppRegistry} from 'react-native';
import App from './App';
import {name as appName} from './app.json';

const MyHeadlessTask = async () => {}

AppRegistry.registerComponent(appName, () => App);
AppRegistry.registerHeadlessTask('Filter', () => MyHeadlessTask);
