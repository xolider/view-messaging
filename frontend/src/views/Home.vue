<template>
    <v-main>
        <v-dialog v-model="usernameDialog" max-width="600" persistent>
            <v-card>
                <v-card-title>Entrez votre nom d'utilisateur</v-card-title>
                <v-card-text>
                    <v-container>
                        <v-row>
                            <v-col>
                                <v-text-field v-model="usernameField" label="Nom d'utilisateur" placeholder="Squeezie" required></v-text-field>
                            </v-col>
                        </v-row>
                    </v-container>
                </v-card-text>
                <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn :disabled="dialogOKButtonEnabled" color="blue darken-1" @click="saveUsername">OK</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
        <v-container fluid style="height: 100%">
            <v-row>
                <v-col class="top-col rounded">
                    <h4 class="text-h4 text-center">
                        Vous êtes connecté sous: {{username}}
                        <v-tooltip bottom>
                            <template v-slot:activator="{on, attr}">
                                <v-btn class="float-right" v-bind="attr" v-on="on" icon @click="disconnect"><v-icon>mdi-logout</v-icon></v-btn>
                            </template>
                            <span>Se déconnecter</span>
                        </v-tooltip>
                    </h4>
                </v-col>
            </v-row>
            <v-row class="messaging-row">
                <v-col></v-col>
            </v-row>
            <v-row>
                <v-col cols="11">
                    <v-text-field hide-details="auto" label="Message" placeholder="Entrez votre message" outlined></v-text-field>
                </v-col>
                <v-col cols="1">
                    <v-btn icon>
                        <v-icon>mdi-send</v-icon>
                    </v-btn>
                </v-col>
            </v-row>
        </v-container>
    </v-main>
</template>

<script>

import store from '../store'

export default {
    name: 'Home',
    computed: {
        username: () => {
            return store.getters.getUsername
        },
        dialogOKButtonEnabled() {
            return this.usernameField === ''
        }
    },
    data() {
        return {
            usernameDialog: false,
            usernameField: ''
        }
    },
    mounted() {
        if(store.getters.getUsername === '') {
            this.usernameDialog = true
        }
    },
    methods: {
        saveUsername() {
            store.dispatch('setUsername', this.usernameField)
            this.usernameDialog = false
        },
        disconnect() {
            store.dispatch('resetUsername')
            this.usernameDialog = true
        }
    }
}
</script>

<style scoped>
.top-col {
    border-bottom: 1px solid silver;
}

.messaging-row {
    height: 77vh;
    border-bottom: 1px solid silver;
}
</style>