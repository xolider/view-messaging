<template>
    <v-main id="Contacts">
        <v-list>
            <v-subheader>UTILISATEURS EN LIGNE</v-subheader>
            <v-list-item-group v-model="selectedContact" color="primary">
                <v-list-item v-for="item in contacts" :key="item.id">
                    <v-list-item-content>
                        <v-list-item-title v-text="item.username"></v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list-item-group>
        </v-list>
    </v-main>
</template>

<script>
import api from '../api'
export default {
    name: 'Contacts',
    props: [
        'value'
    ],
    data() {
        return {
            contacts: []
        }
    },
    mounted() {
        api.getOnlineUsers().then(values => {
            console.log(values.data)
            this.contacts = values.data
        })
    },
    computed: {
        selectedContact: {
            get() {
                return this.contacts.indexOf(this.value)
            },
            set(val) {
                this.$emit('input', this.contacts[val])
            }
        }
    }
}
</script>

<style scoped>
#Contacts {
    border-right: 1px solid silver;
    height: 98vh;
}
</style>