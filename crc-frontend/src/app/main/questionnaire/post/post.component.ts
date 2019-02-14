import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import { MatTabChangeEvent } from '@angular/material';
import { AppUtilService } from 'app/main/util/app.util.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit, AfterViewInit {

  @ViewChild('tabGroup') tabGroup;

  selectedIndex = 0;

  constructor(private appService: AppUtilService) {

  }

  ngOnInit(): void {
    this.appService.getSelectedIndex().subscribe(index => {
      this.selectedIndex = index;
    });
  }

  ngAfterViewInit(): void {

  }

  tabChanged = (tabChangeEvent: MatTabChangeEvent): void => {
    this.appService.saveSelectedTabIndex(tabChangeEvent.index);
  }

}
