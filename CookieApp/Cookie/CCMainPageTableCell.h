//
//  CCMainPageTableCell.h
//  Cookie
//
//  Created by Developer on 16/3/17.
//  Copyright © 2016年 Developer. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CCMainPageTableCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIImageView *topImgView;
@property (weak, nonatomic) IBOutlet UILabel *nameLabel;
@property (weak, nonatomic) IBOutlet UILabel *descLabel;
@property (weak, nonatomic) IBOutlet UILabel *dateTimeLabel;

@end
