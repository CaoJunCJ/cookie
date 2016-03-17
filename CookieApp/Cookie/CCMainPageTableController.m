//
//  CCMainPageTableController.m
//  Cookie
//
//  Created by Developer on 16/3/17.
//  Copyright © 2016年 Developer. All rights reserved.
//

#import "CCMainPageTableController.h"
#import "AFNetworking.h"
#import "CCRecipe.h"
#import "CCMainPageTableCell.h"
#import "UIImageView+WebCache.h"

@implementation CCMainPageTableController

@synthesize recipeList = _recipeList;

- (void)viewDidLoad {
    [self getRandomRecipe];
    [super viewDidLoad];
}

#pragma mark - Table view data source

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 10;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    CCMainPageTableCell *cell = [tableView dequeueReusableCellWithIdentifier:@"mainPageTableCell" forIndexPath:indexPath];
    //NSLog(@"%lu----%lu", (unsigned long)self.recipeList.count, indexPath.row);
    
    if (self.recipeList.count != 10 ) {
        return cell;
    }
    
    CCRecipe *model = self.recipeList[indexPath.row];
    
    [cell.topImgView sd_setImageWithURL:[NSURL URLWithString:model.topImgUrl]];
    cell.nameLabel.text = model.name;
    cell.descLabel.text = model.desc;
    
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    return 300;
}

- (void) getRandomRecipe {
    NSURLSessionConfiguration *configuration = [NSURLSessionConfiguration defaultSessionConfiguration];
    AFURLSessionManager *manager = [[AFURLSessionManager alloc] initWithSessionConfiguration:configuration];
    manager.responseSerializer = [AFHTTPResponseSerializer serializer];
    NSURL *URL = [NSURL URLWithString:@"http://10.0.1.102:8080/CookieServer/v1/RandomRecipe"];
    NSURLRequest *request = [NSURLRequest requestWithURL:URL];
    
    NSURLSessionDataTask *dataTask = [manager dataTaskWithRequest:request completionHandler:^(NSURLResponse *response, id responseObject, NSError *error) {
        if (error) {
            NSLog(@"Error: %@", error);
        } else {
            NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:responseObject options:NSJSONReadingMutableLeaves error:nil];
            [self getAllRecipeWith:dict];
        }
    }];
    [dataTask resume];
}

- (void) getAllRecipeWith:(NSDictionary*) dict {
    if (!dict) {
        return;
    }
    
    NSNumber *result = [dict objectForKey:@"result"];
    
    if (![result isEqualToNumber:@0]) {
        NSLog(@"the result:%@", [dict objectForKey:@"result"]);
        return;
    }
    
    NSArray *data = [dict objectForKey:@"data"];

    NSURLSessionConfiguration *configuration = [NSURLSessionConfiguration defaultSessionConfiguration];
    AFURLSessionManager *manager = [[AFURLSessionManager alloc] initWithSessionConfiguration:configuration];
    manager.responseSerializer = [AFHTTPResponseSerializer serializer];
    
    for (int i=0; i<data.count; i++) {
        NSNumber * recipe_id = [data objectAtIndex:i];
        
        NSURL *URL = [NSURL URLWithString:[NSString stringWithFormat:@"http://10.0.1.102:8080/CookieServer/v1/Recipe?recipe_id=%@", recipe_id]];
        NSURLRequest *request = [NSURLRequest requestWithURL:URL];
        NSURLSessionDataTask *dataTask = [manager dataTaskWithRequest:request completionHandler:^(NSURLResponse *response, id responseObject, NSError *error) {
            if (error) {
                NSLog(@"Error: %@", error);
            } else {
                NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:responseObject options:NSJSONReadingMutableLeaves error:nil];
                
                CCRecipe *recipeModel = [[CCRecipe alloc] initWithDict:[dict objectForKey:@"Recipe"]];
                [_recipeList addObject:recipeModel];
                
                
                dispatch_async(dispatch_get_main_queue(), ^{
                    [self.tableView reloadData];
                    //[self.tableView reloadRowsAtIndexPaths:@[[NSIndexPath indexPathForRow:i inSection:1]] withRowAnimation:UITableViewRowAnimationNone];
                });
                
            }
        }];
        [dataTask resume];
        
    }
}

- (NSMutableArray*)recipeList {
    if (_recipeList == nil) {
        _recipeList = [NSMutableArray array];
    }
    return _recipeList;
}

@end
