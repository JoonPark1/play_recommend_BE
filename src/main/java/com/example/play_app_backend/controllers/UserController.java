package com.example.play_app_backend.controllers;

import com.example.play_app_backend.models.Playlist;
import com.example.play_app_backend.models.PlaylistSong;
import com.example.play_app_backend.models.User;
import com.example.play_app_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:3000")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService s) {
        this.userService = s;
    }

    @PostMapping
    public void addNewUser(@RequestBody User newUser) {
        this.userService.addNewUser(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> validateLogin(@RequestBody User user) {
        User u = userService.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (u != null) {
            return ResponseEntity.ok("Login Successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials!");
        }
    }

    @GetMapping("/playlists")
    public Playlist[] getUserPlaylist(@RequestParam String username, @RequestParam String password) {
        User u = userService.findUserByUsernameAndPassword(username, password);
        if (u != null) {
            return u.getPlaylists();
        } else {
            return null;
        }
    }

    @PostMapping("/addPlaylist")
    public ResponseEntity<Playlist[]> addPlaylistForUser(@RequestParam String username, @RequestParam String password, @RequestBody Playlist p) {
        User u = this.userService.findUserByUsernameAndPassword(username, password);
        if (u != null) {
            Playlist[] playlists = u.getPlaylists();
            Playlist[] extendedPlaylists = Arrays.copyOf(playlists, playlists.length + 1);
            extendedPlaylists[playlists.length] = p;
            u.setPlaylists(extendedPlaylists);
            userService.deleteUserByUsername(u.getUsername());
            userService.addNewUser(u);
            return ResponseEntity.ok().body(extendedPlaylists);
        } else {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/deletePlaylist")
    public ResponseEntity<Playlist[]> deletePlaylistForUser(@RequestParam String username, @RequestParam String password, @RequestParam Object playlistId) {
        System.out.println("deleting playlist with id: " + playlistId);
        User u = this.userService.findUserByUsernameAndPassword(username, password);
        //exists in DB
        if (u != null) {
            Playlist[] playlists = u.getPlaylists();
            ArrayList<Playlist> filteredPlaylists = new ArrayList<Playlist>();
            Object desiredPlaylistId = playlistId;
            //go through each playlist and find one that matches in playlistId!
            for (int i = 0; i < playlists.length; i++) {
                //there is a match!
                if (!playlists[i].getId().equals(desiredPlaylistId)) {
                    System.out.println("Current playlist Id not deleted: " + playlists[i].getId());
                    filteredPlaylists.add(playlists[i]);
                }
            }
            //update user's playlists to filtered playlists!
            Playlist[] convertedFilteredPlaylists = filteredPlaylists.toArray(new Playlist[0]);
            for (int j = 0; j < convertedFilteredPlaylists.length; j++) {
                System.out.println("current playlist Id: " + convertedFilteredPlaylists[j].getId());
                System.out.println("current playlist title: " + convertedFilteredPlaylists[j].getTitle());
            }
            u.setPlaylists(convertedFilteredPlaylists);
            userService.deleteUserByUsername(u.getUsername());
            userService.addNewUser(u);
            return ResponseEntity.ok().body(convertedFilteredPlaylists);
        }
        //doesn't
        else {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("addSong")
    public ResponseEntity<Playlist[]> addSongToPlaylist(@RequestParam String username, @RequestParam String password, @RequestParam String playlistId, @RequestBody PlaylistSong p) {
        User u = this.userService.findUserByUsernameAndPassword(username, password);
        if (u != null) {
            Playlist[] playlists = u.getPlaylists();
            //loop through each playlist till we find one where we have to add song!
            for (int i = 0; i < playlists.length; i++) {
                if (playlists[i].getId().equals(playlistId)) {
                    //get current array of songs pertaining to the specific playlist!
                    PlaylistSong[] playlistSongs = playlists[i].getSongs();
                    PlaylistSong[] extendedSongs = Arrays.copyOf(playlistSongs, playlistSongs.length + 1);
                    //create a new array of songs pertaining specific song!
                    extendedSongs[playlistSongs.length] = p;
                    playlists[i].setSongs(extendedSongs);
                    break;
                }
            }
            //modify user to update with latest uptodate playlist!
            u.setPlaylists(playlists);
            this.userService.deleteUserByUsername(username);
            this.userService.addNewUser(u);
            return ResponseEntity.ok().body(playlists);
        } else {
            return ResponseEntity.status(500).body(null);
        }
    }
}

