<template>
  <v-app id="app">
    <v-app-bar app>
      <v-app-bar-title>Messaging</v-app-bar-title>
      <v-spacer></v-spacer>
      <v-btn href="https://github.com/xolider/view-messaging"><v-icon>mdi-github</v-icon> View on Github</v-btn>
    </v-app-bar>
    <v-row>
      <v-col cols="2">
        <Contacts v-model="selectedContact"/>
      </v-col>
      <v-col cols="10">
        <Home :contact="selectedContact"/>
      </v-col>
    </v-row>
  </v-app>
</template>

<script>

import Home from './views/Home'
import Contacts from './components/Contacts'

export default {
  name: 'App',
  data() {
    return {
      selectedContact: ''
    }
  },
  components: {
    Home,
    Contacts
  }
}
</script>

<style>
#app {
  overflow: hidden;
}
</style>
